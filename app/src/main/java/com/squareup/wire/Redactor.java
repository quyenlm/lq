package com.squareup.wire;

import com.squareup.wire.Message;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Redactor<T extends Message> {
    private static final Redactor<?> NOOP_REDACTOR = new Redactor<Message>((Constructor) null, (List) null, (List) null, (List) null) {
        public Message redact(Message message) {
            return message;
        }
    };
    private static final Map<Class<? extends Message>, Redactor> redactors = new LinkedHashMap();
    private final Constructor<?> builderConstructor;
    private final List<Field> messageFields;
    private final List<Redactor<?>> messageRedactors;
    private final List<Field> redactedFields;

    Redactor(Constructor<?> builderConstructor2, List<Field> redactedFields2, List<Field> messageFields2, List<Redactor<?>> messageRedactors2) {
        this.builderConstructor = builderConstructor2;
        this.redactedFields = redactedFields2;
        this.messageFields = messageFields2;
        this.messageRedactors = messageRedactors2;
    }

    public static synchronized <T extends Message> Redactor<T> get(Class<T> messageClass) {
        Redactor<?> redactor;
        Redactor<?> redactor2;
        synchronized (Redactor.class) {
            redactor = redactors.get(messageClass);
            if (redactor == null) {
                FutureRedactor<T> futureRedactor = new FutureRedactor<>();
                redactors.put(messageClass, futureRedactor);
                try {
                    Class<?> builderClass = Class.forName(messageClass.getName() + "$Builder");
                    List<Field> redactedFields2 = new ArrayList<>();
                    List<Field> messageFields2 = new ArrayList<>();
                    List<Redactor<?>> messageRedactors2 = new ArrayList<>();
                    for (Field messageField : messageClass.getDeclaredFields()) {
                        if (!Modifier.isStatic(messageField.getModifiers())) {
                            ProtoField annotation = (ProtoField) messageField.getAnnotation(ProtoField.class);
                            if (annotation == null || !annotation.redacted()) {
                                if (Message.class.isAssignableFrom(messageField.getType())) {
                                    Field field = builderClass.getDeclaredField(messageField.getName());
                                    Redactor<?> fieldRedactor = get(field.getType());
                                    if (fieldRedactor != NOOP_REDACTOR) {
                                        messageFields2.add(field);
                                        messageRedactors2.add(fieldRedactor);
                                    }
                                }
                            } else {
                                if (annotation.label() == Message.Label.REQUIRED) {
                                    throw new IllegalArgumentException(String.format("Field %s is REQUIRED and cannot be redacted.", new Object[]{messageField}));
                                }
                                redactedFields2.add(builderClass.getDeclaredField(messageField.getName()));
                            }
                        }
                    }
                    if (!redactedFields2.isEmpty() || !messageFields2.isEmpty()) {
                        redactor2 = new Redactor<>(builderClass.getConstructor(new Class[]{messageClass}), redactedFields2, messageFields2, messageRedactors2);
                    } else {
                        redactor2 = NOOP_REDACTOR;
                    }
                    futureRedactor.setDelegate(redactor2);
                    redactors.put(messageClass, redactor2);
                    redactor = redactor2;
                } catch (RuntimeException e) {
                    throw e;
                } catch (Exception e2) {
                    throw new AssertionError(e2);
                }
            }
        }
        return redactor;
    }

    public T redact(T message) {
        if (message == null) {
            return null;
        }
        try {
            Message.Builder<T> builder = (Message.Builder) this.builderConstructor.newInstance(new Object[]{message});
            for (Field field : this.redactedFields) {
                field.set(builder, (Object) null);
            }
            for (int i = 0; i < this.messageFields.size(); i++) {
                Field field2 = this.messageFields.get(i);
                field2.set(builder, this.messageRedactors.get(i).redact((Message) field2.get(builder)));
            }
            return builder.build();
        } catch (Exception e) {
            throw new AssertionError(e.getMessage());
        }
    }

    private static class FutureRedactor<T extends Message> extends Redactor<T> {
        private Redactor<T> delegate;

        public FutureRedactor() {
            super((Constructor<?>) null, (List<Field>) null, (List<Field>) null, (List<Redactor<?>>) null);
        }

        public void setDelegate(Redactor<T> delegate2) {
            this.delegate = delegate2;
        }

        public T redact(T message) {
            if (this.delegate != null) {
                return this.delegate.redact(message);
            }
            throw new IllegalStateException("Delegate was not set.");
        }
    }
}
