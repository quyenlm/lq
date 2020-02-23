package org.jsoup.parser;

import com.facebook.internal.AnalyticsEvents;
import com.facebook.share.internal.ShareConstants;
import com.tencent.component.net.download.multiplex.http.ContentType;
import com.tencent.imsdk.sns.base.IMFriendInfoExViber;
import com.tencent.midas.oversea.comm.APDataReportManager;
import com.tencent.smtt.sdk.TbsVideoCacheTask;
import java.util.ArrayList;
import org.jsoup.helper.StringUtil;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.DocumentType;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Token;

enum HtmlTreeBuilderState {
    Initial {
        /* access modifiers changed from: package-private */
        public boolean process(Token t, HtmlTreeBuilder tb) {
            if (HtmlTreeBuilderState.isWhitespace(t)) {
                return true;
            }
            if (t.isComment()) {
                tb.insert(t.asComment());
                return true;
            } else if (t.isDoctype()) {
                Token.Doctype d = t.asDoctype();
                tb.getDocument().appendChild(new DocumentType(d.getName(), d.getPublicIdentifier(), d.getSystemIdentifier(), tb.getBaseUri()));
                if (d.isForceQuirks()) {
                    tb.getDocument().quirksMode(Document.QuirksMode.quirks);
                }
                tb.transition(BeforeHtml);
                return true;
            } else {
                tb.transition(BeforeHtml);
                return tb.process(t);
            }
        }
    },
    BeforeHtml {
        /* access modifiers changed from: package-private */
        public boolean process(Token t, HtmlTreeBuilder tb) {
            if (t.isDoctype()) {
                tb.error(this);
                return false;
            }
            if (t.isComment()) {
                tb.insert(t.asComment());
            } else if (HtmlTreeBuilderState.isWhitespace(t)) {
                return true;
            } else {
                if (!t.isStartTag() || !t.asStartTag().name().equals(ContentType.SUBTYPE_HTML)) {
                    if (t.isEndTag()) {
                        if (StringUtil.in(t.asEndTag().name(), "head", "body", ContentType.SUBTYPE_HTML, "br")) {
                            return anythingElse(t, tb);
                        }
                    }
                    if (!t.isEndTag()) {
                        return anythingElse(t, tb);
                    }
                    tb.error(this);
                    return false;
                }
                tb.insert(t.asStartTag());
                tb.transition(BeforeHead);
            }
            return true;
        }

        private boolean anythingElse(Token t, HtmlTreeBuilder tb) {
            tb.insertStartTag(ContentType.SUBTYPE_HTML);
            tb.transition(BeforeHead);
            return tb.process(t);
        }
    },
    BeforeHead {
        /* access modifiers changed from: package-private */
        public boolean process(Token t, HtmlTreeBuilder tb) {
            if (HtmlTreeBuilderState.isWhitespace(t)) {
                return true;
            }
            if (t.isComment()) {
                tb.insert(t.asComment());
                return true;
            } else if (t.isDoctype()) {
                tb.error(this);
                return false;
            } else if (t.isStartTag() && t.asStartTag().name().equals(ContentType.SUBTYPE_HTML)) {
                return InBody.process(t, tb);
            } else {
                if (!t.isStartTag() || !t.asStartTag().name().equals("head")) {
                    if (t.isEndTag()) {
                        if (StringUtil.in(t.asEndTag().name(), "head", "body", ContentType.SUBTYPE_HTML, "br")) {
                            tb.processStartTag("head");
                            return tb.process(t);
                        }
                    }
                    if (t.isEndTag()) {
                        tb.error(this);
                        return false;
                    }
                    tb.processStartTag("head");
                    return tb.process(t);
                }
                tb.setHeadElement(tb.insert(t.asStartTag()));
                tb.transition(InHead);
                return true;
            }
        }
    },
    InHead {
        /* access modifiers changed from: package-private */
        public boolean process(Token t, HtmlTreeBuilder tb) {
            if (HtmlTreeBuilderState.isWhitespace(t)) {
                tb.insert(t.asCharacter());
                return true;
            }
            switch (t.type) {
                case Comment:
                    tb.insert(t.asComment());
                    return true;
                case Doctype:
                    tb.error(this);
                    return false;
                case StartTag:
                    Token.StartTag start = t.asStartTag();
                    String name = start.name();
                    if (name.equals(ContentType.SUBTYPE_HTML)) {
                        return InBody.process(t, tb);
                    }
                    if (StringUtil.in(name, "base", "basefont", "bgsound", "command", "link")) {
                        Element el = tb.insertEmpty(start);
                        if (!name.equals("base") || !el.hasAttr(ShareConstants.WEB_DIALOG_PARAM_HREF)) {
                            return true;
                        }
                        tb.maybeSetBaseUri(el);
                        return true;
                    } else if (name.equals("meta")) {
                        tb.insertEmpty(start);
                        return true;
                    } else if (name.equals("title")) {
                        HtmlTreeBuilderState.handleRcData(start, tb);
                        return true;
                    } else {
                        if (StringUtil.in(name, "noframes", AnalyticsEvents.PARAMETER_LIKE_VIEW_STYLE)) {
                            HtmlTreeBuilderState.handleRawtext(start, tb);
                            return true;
                        } else if (name.equals("noscript")) {
                            tb.insert(start);
                            tb.transition(InHeadNoscript);
                            return true;
                        } else if (name.equals("script")) {
                            tb.tokeniser.transition(TokeniserState.ScriptData);
                            tb.markInsertionMode();
                            tb.transition(Text);
                            tb.insert(start);
                            return true;
                        } else if (!name.equals("head")) {
                            return anythingElse(t, tb);
                        } else {
                            tb.error(this);
                            return false;
                        }
                    }
                case EndTag:
                    String name2 = t.asEndTag().name();
                    if (name2.equals("head")) {
                        tb.pop();
                        tb.transition(AfterHead);
                        return true;
                    }
                    if (StringUtil.in(name2, "body", ContentType.SUBTYPE_HTML, "br")) {
                        return anythingElse(t, tb);
                    }
                    tb.error(this);
                    return false;
                default:
                    return anythingElse(t, tb);
            }
        }

        private boolean anythingElse(Token t, TreeBuilder tb) {
            tb.processEndTag("head");
            return tb.process(t);
        }
    },
    InHeadNoscript {
        /* access modifiers changed from: package-private */
        /* JADX WARNING: Code restructure failed: missing block: B:21:0x0086, code lost:
            if (org.jsoup.helper.StringUtil.in(r8.asStartTag().name(), "basefont", "bgsound", "link", "meta", "noframes", com.facebook.internal.AnalyticsEvents.PARAMETER_LIKE_VIEW_STYLE) != false) goto L_0x0088;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:31:0x00c8, code lost:
            if (org.jsoup.helper.StringUtil.in(r8.asStartTag().name(), "head", "noscript") == false) goto L_0x00ca;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean process(org.jsoup.parser.Token r8, org.jsoup.parser.HtmlTreeBuilder r9) {
            /*
                r7 = this;
                r6 = 2
                r1 = 1
                r0 = 0
                boolean r2 = r8.isDoctype()
                if (r2 == 0) goto L_0x000e
                r9.error(r7)
            L_0x000c:
                r0 = r1
            L_0x000d:
                return r0
            L_0x000e:
                boolean r2 = r8.isStartTag()
                if (r2 == 0) goto L_0x002b
                org.jsoup.parser.Token$StartTag r2 = r8.asStartTag()
                java.lang.String r2 = r2.name()
                java.lang.String r3 = "html"
                boolean r2 = r2.equals(r3)
                if (r2 == 0) goto L_0x002b
                org.jsoup.parser.HtmlTreeBuilderState r0 = InBody
                boolean r0 = r9.process(r8, r0)
                goto L_0x000d
            L_0x002b:
                boolean r2 = r8.isEndTag()
                if (r2 == 0) goto L_0x004a
                org.jsoup.parser.Token$EndTag r2 = r8.asEndTag()
                java.lang.String r2 = r2.name()
                java.lang.String r3 = "noscript"
                boolean r2 = r2.equals(r3)
                if (r2 == 0) goto L_0x004a
                r9.pop()
                org.jsoup.parser.HtmlTreeBuilderState r0 = InHead
                r9.transition(r0)
                goto L_0x000c
            L_0x004a:
                boolean r2 = org.jsoup.parser.HtmlTreeBuilderState.isWhitespace((org.jsoup.parser.Token) r8)
                if (r2 != 0) goto L_0x0088
                boolean r2 = r8.isComment()
                if (r2 != 0) goto L_0x0088
                boolean r2 = r8.isStartTag()
                if (r2 == 0) goto L_0x0090
                org.jsoup.parser.Token$StartTag r2 = r8.asStartTag()
                java.lang.String r2 = r2.name()
                r3 = 6
                java.lang.String[] r3 = new java.lang.String[r3]
                java.lang.String r4 = "basefont"
                r3[r0] = r4
                java.lang.String r4 = "bgsound"
                r3[r1] = r4
                java.lang.String r4 = "link"
                r3[r6] = r4
                r4 = 3
                java.lang.String r5 = "meta"
                r3[r4] = r5
                r4 = 4
                java.lang.String r5 = "noframes"
                r3[r4] = r5
                r4 = 5
                java.lang.String r5 = "style"
                r3[r4] = r5
                boolean r2 = org.jsoup.helper.StringUtil.in(r2, r3)
                if (r2 == 0) goto L_0x0090
            L_0x0088:
                org.jsoup.parser.HtmlTreeBuilderState r0 = InHead
                boolean r0 = r9.process(r8, r0)
                goto L_0x000d
            L_0x0090:
                boolean r2 = r8.isEndTag()
                if (r2 == 0) goto L_0x00ac
                org.jsoup.parser.Token$EndTag r2 = r8.asEndTag()
                java.lang.String r2 = r2.name()
                java.lang.String r3 = "br"
                boolean r2 = r2.equals(r3)
                if (r2 == 0) goto L_0x00ac
                boolean r0 = r7.anythingElse(r8, r9)
                goto L_0x000d
            L_0x00ac:
                boolean r2 = r8.isStartTag()
                if (r2 == 0) goto L_0x00ca
                org.jsoup.parser.Token$StartTag r2 = r8.asStartTag()
                java.lang.String r2 = r2.name()
                java.lang.String[] r3 = new java.lang.String[r6]
                java.lang.String r4 = "head"
                r3[r0] = r4
                java.lang.String r4 = "noscript"
                r3[r1] = r4
                boolean r1 = org.jsoup.helper.StringUtil.in(r2, r3)
                if (r1 != 0) goto L_0x00d0
            L_0x00ca:
                boolean r1 = r8.isEndTag()
                if (r1 == 0) goto L_0x00d5
            L_0x00d0:
                r9.error(r7)
                goto L_0x000d
            L_0x00d5:
                boolean r0 = r7.anythingElse(r8, r9)
                goto L_0x000d
            */
            throw new UnsupportedOperationException("Method not decompiled: org.jsoup.parser.HtmlTreeBuilderState.AnonymousClass5.process(org.jsoup.parser.Token, org.jsoup.parser.HtmlTreeBuilder):boolean");
        }

        private boolean anythingElse(Token t, HtmlTreeBuilder tb) {
            tb.error(this);
            tb.insert(new Token.Character().data(t.toString()));
            return true;
        }
    },
    AfterHead {
        /* access modifiers changed from: package-private */
        public boolean process(Token t, HtmlTreeBuilder tb) {
            if (HtmlTreeBuilderState.isWhitespace(t)) {
                tb.insert(t.asCharacter());
            } else if (t.isComment()) {
                tb.insert(t.asComment());
            } else if (t.isDoctype()) {
                tb.error(this);
            } else if (t.isStartTag()) {
                Token.StartTag startTag = t.asStartTag();
                String name = startTag.name();
                if (name.equals(ContentType.SUBTYPE_HTML)) {
                    return tb.process(t, InBody);
                }
                if (name.equals("body")) {
                    tb.insert(startTag);
                    tb.framesetOk(false);
                    tb.transition(InBody);
                } else if (name.equals("frameset")) {
                    tb.insert(startTag);
                    tb.transition(InFrameset);
                } else {
                    if (StringUtil.in(name, "base", "basefont", "bgsound", "link", "meta", "noframes", "script", AnalyticsEvents.PARAMETER_LIKE_VIEW_STYLE, "title")) {
                        tb.error(this);
                        Element head = tb.getHeadElement();
                        tb.push(head);
                        tb.process(t, InHead);
                        tb.removeFromStack(head);
                    } else if (name.equals("head")) {
                        tb.error(this);
                        return false;
                    } else {
                        anythingElse(t, tb);
                    }
                }
            } else if (t.isEndTag()) {
                if (StringUtil.in(t.asEndTag().name(), "body", ContentType.SUBTYPE_HTML)) {
                    anythingElse(t, tb);
                } else {
                    tb.error(this);
                    return false;
                }
            } else {
                anythingElse(t, tb);
            }
            return true;
        }

        private boolean anythingElse(Token t, HtmlTreeBuilder tb) {
            tb.processStartTag("body");
            tb.framesetOk(true);
            return tb.process(t);
        }
    },
    InBody {
        /* access modifiers changed from: package-private */
        /* JADX WARNING: Removed duplicated region for block: B:305:0x0a5c  */
        /* JADX WARNING: Removed duplicated region for block: B:312:0x0aa7 A[LOOP:9: B:310:0x0aa1->B:312:0x0aa7, LOOP_END] */
        /* JADX WARNING: Removed duplicated region for block: B:319:0x0af4  */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean process(org.jsoup.parser.Token r40, org.jsoup.parser.HtmlTreeBuilder r41) {
            /*
                r39 = this;
                int[] r36 = org.jsoup.parser.HtmlTreeBuilderState.AnonymousClass24.$SwitchMap$org$jsoup$parser$Token$TokenType
                r0 = r40
                org.jsoup.parser.Token$TokenType r0 = r0.type
                r37 = r0
                int r37 = r37.ordinal()
                r36 = r36[r37]
                switch(r36) {
                    case 1: goto L_0x0057;
                    case 2: goto L_0x0063;
                    case 3: goto L_0x006d;
                    case 4: goto L_0x093e;
                    case 5: goto L_0x0014;
                    default: goto L_0x0011;
                }
            L_0x0011:
                r36 = 1
            L_0x0013:
                return r36
            L_0x0014:
                org.jsoup.parser.Token$Character r8 = r40.asCharacter()
                java.lang.String r36 = r8.getData()
                java.lang.String r37 = org.jsoup.parser.HtmlTreeBuilderState.nullString
                boolean r36 = r36.equals(r37)
                if (r36 == 0) goto L_0x0030
                r0 = r41
                r1 = r39
                r0.error(r1)
                r36 = 0
                goto L_0x0013
            L_0x0030:
                boolean r36 = r41.framesetOk()
                if (r36 == 0) goto L_0x0045
                boolean r36 = org.jsoup.parser.HtmlTreeBuilderState.isWhitespace((org.jsoup.parser.Token) r8)
                if (r36 == 0) goto L_0x0045
                r41.reconstructFormattingElements()
                r0 = r41
                r0.insert((org.jsoup.parser.Token.Character) r8)
                goto L_0x0011
            L_0x0045:
                r41.reconstructFormattingElements()
                r0 = r41
                r0.insert((org.jsoup.parser.Token.Character) r8)
                r36 = 0
                r0 = r41
                r1 = r36
                r0.framesetOk(r1)
                goto L_0x0011
            L_0x0057:
                org.jsoup.parser.Token$Comment r36 = r40.asComment()
                r0 = r41
                r1 = r36
                r0.insert((org.jsoup.parser.Token.Comment) r1)
                goto L_0x0011
            L_0x0063:
                r0 = r41
                r1 = r39
                r0.error(r1)
                r36 = 0
                goto L_0x0013
            L_0x006d:
                org.jsoup.parser.Token$StartTag r34 = r40.asStartTag()
                java.lang.String r23 = r34.name()
                java.lang.String r36 = "a"
                r0 = r23
                r1 = r36
                boolean r36 = r0.equals(r1)
                if (r36 == 0) goto L_0x00c9
                java.lang.String r36 = "a"
                r0 = r41
                r1 = r36
                org.jsoup.nodes.Element r36 = r0.getActiveFormattingElement(r1)
                if (r36 == 0) goto L_0x00b7
                r0 = r41
                r1 = r39
                r0.error(r1)
                java.lang.String r36 = "a"
                r0 = r41
                r1 = r36
                r0.processEndTag(r1)
                java.lang.String r36 = "a"
                r0 = r41
                r1 = r36
                org.jsoup.nodes.Element r27 = r0.getFromStack(r1)
                if (r27 == 0) goto L_0x00b7
                r0 = r41
                r1 = r27
                r0.removeFromActiveFormattingElements(r1)
                r0 = r41
                r1 = r27
                r0.removeFromStack(r1)
            L_0x00b7:
                r41.reconstructFormattingElements()
                r0 = r41
                r1 = r34
                org.jsoup.nodes.Element r3 = r0.insert((org.jsoup.parser.Token.StartTag) r1)
                r0 = r41
                r0.pushActiveFormattingElements(r3)
                goto L_0x0011
            L_0x00c9:
                java.lang.String[] r36 = org.jsoup.parser.HtmlTreeBuilderState.Constants.InBodyStartEmptyFormatters
                r0 = r23
                r1 = r36
                boolean r36 = org.jsoup.helper.StringUtil.inSorted(r0, r1)
                if (r36 == 0) goto L_0x00ec
                r41.reconstructFormattingElements()
                r0 = r41
                r1 = r34
                r0.insertEmpty(r1)
                r36 = 0
                r0 = r41
                r1 = r36
                r0.framesetOk(r1)
                goto L_0x0011
            L_0x00ec:
                java.lang.String[] r36 = org.jsoup.parser.HtmlTreeBuilderState.Constants.InBodyStartPClosers
                r0 = r23
                r1 = r36
                boolean r36 = org.jsoup.helper.StringUtil.inSorted(r0, r1)
                if (r36 == 0) goto L_0x0118
                java.lang.String r36 = "p"
                r0 = r41
                r1 = r36
                boolean r36 = r0.inButtonScope(r1)
                if (r36 == 0) goto L_0x010f
                java.lang.String r36 = "p"
                r0 = r41
                r1 = r36
                r0.processEndTag(r1)
            L_0x010f:
                r0 = r41
                r1 = r34
                r0.insert((org.jsoup.parser.Token.StartTag) r1)
                goto L_0x0011
            L_0x0118:
                java.lang.String r36 = "span"
                r0 = r23
                r1 = r36
                boolean r36 = r0.equals(r1)
                if (r36 == 0) goto L_0x0130
                r41.reconstructFormattingElements()
                r0 = r41
                r1 = r34
                r0.insert((org.jsoup.parser.Token.StartTag) r1)
                goto L_0x0011
            L_0x0130:
                java.lang.String r36 = "li"
                r0 = r23
                r1 = r36
                boolean r36 = r0.equals(r1)
                if (r36 == 0) goto L_0x01a7
                r36 = 0
                r0 = r41
                r1 = r36
                r0.framesetOk(r1)
                java.util.ArrayList r32 = r41.getStack()
                int r36 = r32.size()
                int r19 = r36 + -1
            L_0x014f:
                if (r19 <= 0) goto L_0x0170
                r0 = r32
                r1 = r19
                java.lang.Object r13 = r0.get(r1)
                org.jsoup.nodes.Element r13 = (org.jsoup.nodes.Element) r13
                java.lang.String r36 = r13.nodeName()
                java.lang.String r37 = "li"
                boolean r36 = r36.equals(r37)
                if (r36 == 0) goto L_0x018e
                java.lang.String r36 = "li"
                r0 = r41
                r1 = r36
                r0.processEndTag(r1)
            L_0x0170:
                java.lang.String r36 = "p"
                r0 = r41
                r1 = r36
                boolean r36 = r0.inButtonScope(r1)
                if (r36 == 0) goto L_0x0185
                java.lang.String r36 = "p"
                r0 = r41
                r1 = r36
                r0.processEndTag(r1)
            L_0x0185:
                r0 = r41
                r1 = r34
                r0.insert((org.jsoup.parser.Token.StartTag) r1)
                goto L_0x0011
            L_0x018e:
                r0 = r41
                boolean r36 = r0.isSpecial(r13)
                if (r36 == 0) goto L_0x01a4
                java.lang.String r36 = r13.nodeName()
                java.lang.String[] r37 = org.jsoup.parser.HtmlTreeBuilderState.Constants.InBodyStartLiBreakers
                boolean r36 = org.jsoup.helper.StringUtil.inSorted(r36, r37)
                if (r36 == 0) goto L_0x0170
            L_0x01a4:
                int r19 = r19 + -1
                goto L_0x014f
            L_0x01a7:
                java.lang.String r36 = "html"
                r0 = r23
                r1 = r36
                boolean r36 = r0.equals(r1)
                if (r36 == 0) goto L_0x01f2
                r0 = r41
                r1 = r39
                r0.error(r1)
                java.util.ArrayList r36 = r41.getStack()
                r37 = 0
                java.lang.Object r18 = r36.get(r37)
                org.jsoup.nodes.Element r18 = (org.jsoup.nodes.Element) r18
                org.jsoup.nodes.Attributes r36 = r34.getAttributes()
                java.util.Iterator r36 = r36.iterator()
            L_0x01ce:
                boolean r37 = r36.hasNext()
                if (r37 == 0) goto L_0x0011
                java.lang.Object r6 = r36.next()
                org.jsoup.nodes.Attribute r6 = (org.jsoup.nodes.Attribute) r6
                java.lang.String r37 = r6.getKey()
                r0 = r18
                r1 = r37
                boolean r37 = r0.hasAttr(r1)
                if (r37 != 0) goto L_0x01ce
                org.jsoup.nodes.Attributes r37 = r18.attributes()
                r0 = r37
                r0.put(r6)
                goto L_0x01ce
            L_0x01f2:
                java.lang.String[] r36 = org.jsoup.parser.HtmlTreeBuilderState.Constants.InBodyStartToHead
                r0 = r23
                r1 = r36
                boolean r36 = org.jsoup.helper.StringUtil.inSorted(r0, r1)
                if (r36 == 0) goto L_0x020e
                org.jsoup.parser.HtmlTreeBuilderState r36 = InHead
                r0 = r41
                r1 = r40
                r2 = r36
                boolean r36 = r0.process(r1, r2)
                goto L_0x0013
            L_0x020e:
                java.lang.String r36 = "body"
                r0 = r23
                r1 = r36
                boolean r36 = r0.equals(r1)
                if (r36 == 0) goto L_0x0298
                r0 = r41
                r1 = r39
                r0.error(r1)
                java.util.ArrayList r32 = r41.getStack()
                int r36 = r32.size()
                r37 = 1
                r0 = r36
                r1 = r37
                if (r0 == r1) goto L_0x0255
                int r36 = r32.size()
                r37 = 2
                r0 = r36
                r1 = r37
                if (r0 <= r1) goto L_0x0259
                r36 = 1
                r0 = r32
                r1 = r36
                java.lang.Object r36 = r0.get(r1)
                org.jsoup.nodes.Element r36 = (org.jsoup.nodes.Element) r36
                java.lang.String r36 = r36.nodeName()
                java.lang.String r37 = "body"
                boolean r36 = r36.equals(r37)
                if (r36 != 0) goto L_0x0259
            L_0x0255:
                r36 = 0
                goto L_0x0013
            L_0x0259:
                r36 = 0
                r0 = r41
                r1 = r36
                r0.framesetOk(r1)
                r36 = 1
                r0 = r32
                r1 = r36
                java.lang.Object r7 = r0.get(r1)
                org.jsoup.nodes.Element r7 = (org.jsoup.nodes.Element) r7
                org.jsoup.nodes.Attributes r36 = r34.getAttributes()
                java.util.Iterator r36 = r36.iterator()
            L_0x0276:
                boolean r37 = r36.hasNext()
                if (r37 == 0) goto L_0x0011
                java.lang.Object r6 = r36.next()
                org.jsoup.nodes.Attribute r6 = (org.jsoup.nodes.Attribute) r6
                java.lang.String r37 = r6.getKey()
                r0 = r37
                boolean r37 = r7.hasAttr(r0)
                if (r37 != 0) goto L_0x0276
                org.jsoup.nodes.Attributes r37 = r7.attributes()
                r0 = r37
                r0.put(r6)
                goto L_0x0276
            L_0x0298:
                java.lang.String r36 = "frameset"
                r0 = r23
                r1 = r36
                boolean r36 = r0.equals(r1)
                if (r36 == 0) goto L_0x032e
                r0 = r41
                r1 = r39
                r0.error(r1)
                java.util.ArrayList r32 = r41.getStack()
                int r36 = r32.size()
                r37 = 1
                r0 = r36
                r1 = r37
                if (r0 == r1) goto L_0x02df
                int r36 = r32.size()
                r37 = 2
                r0 = r36
                r1 = r37
                if (r0 <= r1) goto L_0x02e3
                r36 = 1
                r0 = r32
                r1 = r36
                java.lang.Object r36 = r0.get(r1)
                org.jsoup.nodes.Element r36 = (org.jsoup.nodes.Element) r36
                java.lang.String r36 = r36.nodeName()
                java.lang.String r37 = "body"
                boolean r36 = r36.equals(r37)
                if (r36 != 0) goto L_0x02e3
            L_0x02df:
                r36 = 0
                goto L_0x0013
            L_0x02e3:
                boolean r36 = r41.framesetOk()
                if (r36 != 0) goto L_0x02ed
                r36 = 0
                goto L_0x0013
            L_0x02ed:
                r36 = 1
                r0 = r32
                r1 = r36
                java.lang.Object r29 = r0.get(r1)
                org.jsoup.nodes.Element r29 = (org.jsoup.nodes.Element) r29
                org.jsoup.nodes.Element r36 = r29.parent()
                if (r36 == 0) goto L_0x0302
                r29.remove()
            L_0x0302:
                int r36 = r32.size()
                r37 = 1
                r0 = r36
                r1 = r37
                if (r0 <= r1) goto L_0x031c
                int r36 = r32.size()
                int r36 = r36 + -1
                r0 = r32
                r1 = r36
                r0.remove(r1)
                goto L_0x0302
            L_0x031c:
                r0 = r41
                r1 = r34
                r0.insert((org.jsoup.parser.Token.StartTag) r1)
                org.jsoup.parser.HtmlTreeBuilderState r36 = InFrameset
                r0 = r41
                r1 = r36
                r0.transition(r1)
                goto L_0x0011
            L_0x032e:
                java.lang.String[] r36 = org.jsoup.parser.HtmlTreeBuilderState.Constants.Headings
                r0 = r23
                r1 = r36
                boolean r36 = org.jsoup.helper.StringUtil.inSorted(r0, r1)
                if (r36 == 0) goto L_0x0376
                java.lang.String r36 = "p"
                r0 = r41
                r1 = r36
                boolean r36 = r0.inButtonScope(r1)
                if (r36 == 0) goto L_0x0351
                java.lang.String r36 = "p"
                r0 = r41
                r1 = r36
                r0.processEndTag(r1)
            L_0x0351:
                org.jsoup.nodes.Element r36 = r41.currentElement()
                java.lang.String r36 = r36.nodeName()
                java.lang.String[] r37 = org.jsoup.parser.HtmlTreeBuilderState.Constants.Headings
                boolean r36 = org.jsoup.helper.StringUtil.inSorted(r36, r37)
                if (r36 == 0) goto L_0x036d
                r0 = r41
                r1 = r39
                r0.error(r1)
                r41.pop()
            L_0x036d:
                r0 = r41
                r1 = r34
                r0.insert((org.jsoup.parser.Token.StartTag) r1)
                goto L_0x0011
            L_0x0376:
                java.lang.String[] r36 = org.jsoup.parser.HtmlTreeBuilderState.Constants.InBodyStartPreListing
                r0 = r23
                r1 = r36
                boolean r36 = org.jsoup.helper.StringUtil.inSorted(r0, r1)
                if (r36 == 0) goto L_0x03ab
                java.lang.String r36 = "p"
                r0 = r41
                r1 = r36
                boolean r36 = r0.inButtonScope(r1)
                if (r36 == 0) goto L_0x0399
                java.lang.String r36 = "p"
                r0 = r41
                r1 = r36
                r0.processEndTag(r1)
            L_0x0399:
                r0 = r41
                r1 = r34
                r0.insert((org.jsoup.parser.Token.StartTag) r1)
                r36 = 0
                r0 = r41
                r1 = r36
                r0.framesetOk(r1)
                goto L_0x0011
            L_0x03ab:
                java.lang.String r36 = "form"
                r0 = r23
                r1 = r36
                boolean r36 = r0.equals(r1)
                if (r36 == 0) goto L_0x03ea
                org.jsoup.nodes.FormElement r36 = r41.getFormElement()
                if (r36 == 0) goto L_0x03c8
                r0 = r41
                r1 = r39
                r0.error(r1)
                r36 = 0
                goto L_0x0013
            L_0x03c8:
                java.lang.String r36 = "p"
                r0 = r41
                r1 = r36
                boolean r36 = r0.inButtonScope(r1)
                if (r36 == 0) goto L_0x03dd
                java.lang.String r36 = "p"
                r0 = r41
                r1 = r36
                r0.processEndTag(r1)
            L_0x03dd:
                r36 = 1
                r0 = r41
                r1 = r34
                r2 = r36
                r0.insertForm(r1, r2)
                goto L_0x0011
            L_0x03ea:
                java.lang.String[] r36 = org.jsoup.parser.HtmlTreeBuilderState.Constants.DdDt
                r0 = r23
                r1 = r36
                boolean r36 = org.jsoup.helper.StringUtil.inSorted(r0, r1)
                if (r36 == 0) goto L_0x0467
                r36 = 0
                r0 = r41
                r1 = r36
                r0.framesetOk(r1)
                java.util.ArrayList r32 = r41.getStack()
                int r36 = r32.size()
                int r19 = r36 + -1
            L_0x040b:
                if (r19 <= 0) goto L_0x0430
                r0 = r32
                r1 = r19
                java.lang.Object r13 = r0.get(r1)
                org.jsoup.nodes.Element r13 = (org.jsoup.nodes.Element) r13
                java.lang.String r36 = r13.nodeName()
                java.lang.String[] r37 = org.jsoup.parser.HtmlTreeBuilderState.Constants.DdDt
                boolean r36 = org.jsoup.helper.StringUtil.inSorted(r36, r37)
                if (r36 == 0) goto L_0x044e
                java.lang.String r36 = r13.nodeName()
                r0 = r41
                r1 = r36
                r0.processEndTag(r1)
            L_0x0430:
                java.lang.String r36 = "p"
                r0 = r41
                r1 = r36
                boolean r36 = r0.inButtonScope(r1)
                if (r36 == 0) goto L_0x0445
                java.lang.String r36 = "p"
                r0 = r41
                r1 = r36
                r0.processEndTag(r1)
            L_0x0445:
                r0 = r41
                r1 = r34
                r0.insert((org.jsoup.parser.Token.StartTag) r1)
                goto L_0x0011
            L_0x044e:
                r0 = r41
                boolean r36 = r0.isSpecial(r13)
                if (r36 == 0) goto L_0x0464
                java.lang.String r36 = r13.nodeName()
                java.lang.String[] r37 = org.jsoup.parser.HtmlTreeBuilderState.Constants.InBodyStartLiBreakers
                boolean r36 = org.jsoup.helper.StringUtil.inSorted(r36, r37)
                if (r36 == 0) goto L_0x0430
            L_0x0464:
                int r19 = r19 + -1
                goto L_0x040b
            L_0x0467:
                java.lang.String r36 = "plaintext"
                r0 = r23
                r1 = r36
                boolean r36 = r0.equals(r1)
                if (r36 == 0) goto L_0x049c
                java.lang.String r36 = "p"
                r0 = r41
                r1 = r36
                boolean r36 = r0.inButtonScope(r1)
                if (r36 == 0) goto L_0x0488
                java.lang.String r36 = "p"
                r0 = r41
                r1 = r36
                r0.processEndTag(r1)
            L_0x0488:
                r0 = r41
                r1 = r34
                r0.insert((org.jsoup.parser.Token.StartTag) r1)
                r0 = r41
                org.jsoup.parser.Tokeniser r0 = r0.tokeniser
                r36 = r0
                org.jsoup.parser.TokeniserState r37 = org.jsoup.parser.TokeniserState.PLAINTEXT
                r36.transition(r37)
                goto L_0x0011
            L_0x049c:
                java.lang.String r36 = "button"
                r0 = r23
                r1 = r36
                boolean r36 = r0.equals(r1)
                if (r36 == 0) goto L_0x04e2
                java.lang.String r36 = "button"
                r0 = r41
                r1 = r36
                boolean r36 = r0.inButtonScope(r1)
                if (r36 == 0) goto L_0x04cd
                r0 = r41
                r1 = r39
                r0.error(r1)
                java.lang.String r36 = "button"
                r0 = r41
                r1 = r36
                r0.processEndTag(r1)
                r0 = r41
                r1 = r34
                r0.process(r1)
                goto L_0x0011
            L_0x04cd:
                r41.reconstructFormattingElements()
                r0 = r41
                r1 = r34
                r0.insert((org.jsoup.parser.Token.StartTag) r1)
                r36 = 0
                r0 = r41
                r1 = r36
                r0.framesetOk(r1)
                goto L_0x0011
            L_0x04e2:
                java.lang.String[] r36 = org.jsoup.parser.HtmlTreeBuilderState.Constants.Formatters
                r0 = r23
                r1 = r36
                boolean r36 = org.jsoup.helper.StringUtil.inSorted(r0, r1)
                if (r36 == 0) goto L_0x0502
                r41.reconstructFormattingElements()
                r0 = r41
                r1 = r34
                org.jsoup.nodes.Element r13 = r0.insert((org.jsoup.parser.Token.StartTag) r1)
                r0 = r41
                r0.pushActiveFormattingElements(r13)
                goto L_0x0011
            L_0x0502:
                java.lang.String r36 = "nobr"
                r0 = r23
                r1 = r36
                boolean r36 = r0.equals(r1)
                if (r36 == 0) goto L_0x053f
                r41.reconstructFormattingElements()
                java.lang.String r36 = "nobr"
                r0 = r41
                r1 = r36
                boolean r36 = r0.inScope((java.lang.String) r1)
                if (r36 == 0) goto L_0x0530
                r0 = r41
                r1 = r39
                r0.error(r1)
                java.lang.String r36 = "nobr"
                r0 = r41
                r1 = r36
                r0.processEndTag(r1)
                r41.reconstructFormattingElements()
            L_0x0530:
                r0 = r41
                r1 = r34
                org.jsoup.nodes.Element r13 = r0.insert((org.jsoup.parser.Token.StartTag) r1)
                r0 = r41
                r0.pushActiveFormattingElements(r13)
                goto L_0x0011
            L_0x053f:
                java.lang.String[] r36 = org.jsoup.parser.HtmlTreeBuilderState.Constants.InBodyStartApplets
                r0 = r23
                r1 = r36
                boolean r36 = org.jsoup.helper.StringUtil.inSorted(r0, r1)
                if (r36 == 0) goto L_0x0565
                r41.reconstructFormattingElements()
                r0 = r41
                r1 = r34
                r0.insert((org.jsoup.parser.Token.StartTag) r1)
                r41.insertMarkerToFormattingElements()
                r36 = 0
                r0 = r41
                r1 = r36
                r0.framesetOk(r1)
                goto L_0x0011
            L_0x0565:
                java.lang.String r36 = "table"
                r0 = r23
                r1 = r36
                boolean r36 = r0.equals(r1)
                if (r36 == 0) goto L_0x05b1
                org.jsoup.nodes.Document r36 = r41.getDocument()
                org.jsoup.nodes.Document$QuirksMode r36 = r36.quirksMode()
                org.jsoup.nodes.Document$QuirksMode r37 = org.jsoup.nodes.Document.QuirksMode.quirks
                r0 = r36
                r1 = r37
                if (r0 == r1) goto L_0x0596
                java.lang.String r36 = "p"
                r0 = r41
                r1 = r36
                boolean r36 = r0.inButtonScope(r1)
                if (r36 == 0) goto L_0x0596
                java.lang.String r36 = "p"
                r0 = r41
                r1 = r36
                r0.processEndTag(r1)
            L_0x0596:
                r0 = r41
                r1 = r34
                r0.insert((org.jsoup.parser.Token.StartTag) r1)
                r36 = 0
                r0 = r41
                r1 = r36
                r0.framesetOk(r1)
                org.jsoup.parser.HtmlTreeBuilderState r36 = InTable
                r0 = r41
                r1 = r36
                r0.transition(r1)
                goto L_0x0011
            L_0x05b1:
                java.lang.String r36 = "input"
                r0 = r23
                r1 = r36
                boolean r36 = r0.equals(r1)
                if (r36 == 0) goto L_0x05e3
                r41.reconstructFormattingElements()
                r0 = r41
                r1 = r34
                org.jsoup.nodes.Element r13 = r0.insertEmpty(r1)
                java.lang.String r36 = "type"
                r0 = r36
                java.lang.String r36 = r13.attr(r0)
                java.lang.String r37 = "hidden"
                boolean r36 = r36.equalsIgnoreCase(r37)
                if (r36 != 0) goto L_0x0011
                r36 = 0
                r0 = r41
                r1 = r36
                r0.framesetOk(r1)
                goto L_0x0011
            L_0x05e3:
                java.lang.String[] r36 = org.jsoup.parser.HtmlTreeBuilderState.Constants.InBodyStartMedia
                r0 = r23
                r1 = r36
                boolean r36 = org.jsoup.helper.StringUtil.inSorted(r0, r1)
                if (r36 == 0) goto L_0x05fa
                r0 = r41
                r1 = r34
                r0.insertEmpty(r1)
                goto L_0x0011
            L_0x05fa:
                java.lang.String r36 = "hr"
                r0 = r23
                r1 = r36
                boolean r36 = r0.equals(r1)
                if (r36 == 0) goto L_0x062d
                java.lang.String r36 = "p"
                r0 = r41
                r1 = r36
                boolean r36 = r0.inButtonScope(r1)
                if (r36 == 0) goto L_0x061b
                java.lang.String r36 = "p"
                r0 = r41
                r1 = r36
                r0.processEndTag(r1)
            L_0x061b:
                r0 = r41
                r1 = r34
                r0.insertEmpty(r1)
                r36 = 0
                r0 = r41
                r1 = r36
                r0.framesetOk(r1)
                goto L_0x0011
            L_0x062d:
                java.lang.String r36 = "image"
                r0 = r23
                r1 = r36
                boolean r36 = r0.equals(r1)
                if (r36 == 0) goto L_0x0662
                java.lang.String r36 = "svg"
                r0 = r41
                r1 = r36
                org.jsoup.nodes.Element r36 = r0.getFromStack(r1)
                if (r36 != 0) goto L_0x0659
                java.lang.String r36 = "img"
                r0 = r34
                r1 = r36
                org.jsoup.parser.Token$Tag r36 = r0.name(r1)
                r0 = r41
                r1 = r36
                boolean r36 = r0.process(r1)
                goto L_0x0013
            L_0x0659:
                r0 = r41
                r1 = r34
                r0.insert((org.jsoup.parser.Token.StartTag) r1)
                goto L_0x0011
            L_0x0662:
                java.lang.String r36 = "isindex"
                r0 = r23
                r1 = r36
                boolean r36 = r0.equals(r1)
                if (r36 == 0) goto L_0x075f
                r0 = r41
                r1 = r39
                r0.error(r1)
                org.jsoup.nodes.FormElement r36 = r41.getFormElement()
                if (r36 == 0) goto L_0x067f
                r36 = 0
                goto L_0x0013
            L_0x067f:
                r0 = r41
                org.jsoup.parser.Tokeniser r0 = r0.tokeniser
                r36 = r0
                r36.acknowledgeSelfClosingFlag()
                java.lang.String r36 = "form"
                r0 = r41
                r1 = r36
                r0.processStartTag(r1)
                r0 = r34
                org.jsoup.nodes.Attributes r0 = r0.attributes
                r36 = r0
                java.lang.String r37 = "action"
                boolean r36 = r36.hasKey(r37)
                if (r36 == 0) goto L_0x06b8
                org.jsoup.nodes.FormElement r15 = r41.getFormElement()
                java.lang.String r36 = "action"
                r0 = r34
                org.jsoup.nodes.Attributes r0 = r0.attributes
                r37 = r0
                java.lang.String r38 = "action"
                java.lang.String r37 = r37.get(r38)
                r0 = r36
                r1 = r37
                r15.attr((java.lang.String) r0, (java.lang.String) r1)
            L_0x06b8:
                java.lang.String r36 = "hr"
                r0 = r41
                r1 = r36
                r0.processStartTag(r1)
                java.lang.String r36 = "label"
                r0 = r41
                r1 = r36
                r0.processStartTag(r1)
                r0 = r34
                org.jsoup.nodes.Attributes r0 = r0.attributes
                r36 = r0
                java.lang.String r37 = "prompt"
                boolean r36 = r36.hasKey(r37)
                if (r36 == 0) goto L_0x0727
                r0 = r34
                org.jsoup.nodes.Attributes r0 = r0.attributes
                r36 = r0
                java.lang.String r37 = "prompt"
                java.lang.String r26 = r36.get(r37)
            L_0x06e4:
                org.jsoup.parser.Token$Character r36 = new org.jsoup.parser.Token$Character
                r36.<init>()
                r0 = r36
                r1 = r26
                org.jsoup.parser.Token$Character r36 = r0.data(r1)
                r0 = r41
                r1 = r36
                r0.process(r1)
                org.jsoup.nodes.Attributes r20 = new org.jsoup.nodes.Attributes
                r20.<init>()
                r0 = r34
                org.jsoup.nodes.Attributes r0 = r0.attributes
                r36 = r0
                java.util.Iterator r36 = r36.iterator()
            L_0x0707:
                boolean r37 = r36.hasNext()
                if (r37 == 0) goto L_0x072a
                java.lang.Object r5 = r36.next()
                org.jsoup.nodes.Attribute r5 = (org.jsoup.nodes.Attribute) r5
                java.lang.String r37 = r5.getKey()
                java.lang.String[] r38 = org.jsoup.parser.HtmlTreeBuilderState.Constants.InBodyStartInputAttribs
                boolean r37 = org.jsoup.helper.StringUtil.inSorted(r37, r38)
                if (r37 != 0) goto L_0x0707
                r0 = r20
                r0.put(r5)
                goto L_0x0707
            L_0x0727:
                java.lang.String r26 = "This is a searchable index. Enter search keywords: "
                goto L_0x06e4
            L_0x072a:
                java.lang.String r36 = "name"
                java.lang.String r37 = "isindex"
                r0 = r20
                r1 = r36
                r2 = r37
                r0.put((java.lang.String) r1, (java.lang.String) r2)
                java.lang.String r36 = "input"
                r0 = r41
                r1 = r36
                r2 = r20
                r0.processStartTag(r1, r2)
                java.lang.String r36 = "label"
                r0 = r41
                r1 = r36
                r0.processEndTag(r1)
                java.lang.String r36 = "hr"
                r0 = r41
                r1 = r36
                r0.processStartTag(r1)
                java.lang.String r36 = "form"
                r0 = r41
                r1 = r36
                r0.processEndTag(r1)
                goto L_0x0011
            L_0x075f:
                java.lang.String r36 = "textarea"
                r0 = r23
                r1 = r36
                boolean r36 = r0.equals(r1)
                if (r36 == 0) goto L_0x0794
                r0 = r41
                r1 = r34
                r0.insert((org.jsoup.parser.Token.StartTag) r1)
                r0 = r41
                org.jsoup.parser.Tokeniser r0 = r0.tokeniser
                r36 = r0
                org.jsoup.parser.TokeniserState r37 = org.jsoup.parser.TokeniserState.Rcdata
                r36.transition(r37)
                r41.markInsertionMode()
                r36 = 0
                r0 = r41
                r1 = r36
                r0.framesetOk(r1)
                org.jsoup.parser.HtmlTreeBuilderState r36 = Text
                r0 = r41
                r1 = r36
                r0.transition(r1)
                goto L_0x0011
            L_0x0794:
                java.lang.String r36 = "xmp"
                r0 = r23
                r1 = r36
                boolean r36 = r0.equals(r1)
                if (r36 == 0) goto L_0x07ca
                java.lang.String r36 = "p"
                r0 = r41
                r1 = r36
                boolean r36 = r0.inButtonScope(r1)
                if (r36 == 0) goto L_0x07b5
                java.lang.String r36 = "p"
                r0 = r41
                r1 = r36
                r0.processEndTag(r1)
            L_0x07b5:
                r41.reconstructFormattingElements()
                r36 = 0
                r0 = r41
                r1 = r36
                r0.framesetOk(r1)
                r0 = r34
                r1 = r41
                org.jsoup.parser.HtmlTreeBuilderState.handleRawtext(r0, r1)
                goto L_0x0011
            L_0x07ca:
                java.lang.String r36 = "iframe"
                r0 = r23
                r1 = r36
                boolean r36 = r0.equals(r1)
                if (r36 == 0) goto L_0x07e8
                r36 = 0
                r0 = r41
                r1 = r36
                r0.framesetOk(r1)
                r0 = r34
                r1 = r41
                org.jsoup.parser.HtmlTreeBuilderState.handleRawtext(r0, r1)
                goto L_0x0011
            L_0x07e8:
                java.lang.String r36 = "noembed"
                r0 = r23
                r1 = r36
                boolean r36 = r0.equals(r1)
                if (r36 == 0) goto L_0x07fd
                r0 = r34
                r1 = r41
                org.jsoup.parser.HtmlTreeBuilderState.handleRawtext(r0, r1)
                goto L_0x0011
            L_0x07fd:
                java.lang.String r36 = "select"
                r0 = r23
                r1 = r36
                boolean r36 = r0.equals(r1)
                if (r36 == 0) goto L_0x085e
                r41.reconstructFormattingElements()
                r0 = r41
                r1 = r34
                r0.insert((org.jsoup.parser.Token.StartTag) r1)
                r36 = 0
                r0 = r41
                r1 = r36
                r0.framesetOk(r1)
                org.jsoup.parser.HtmlTreeBuilderState r35 = r41.state()
                org.jsoup.parser.HtmlTreeBuilderState r36 = InTable
                boolean r36 = r35.equals(r36)
                if (r36 != 0) goto L_0x0848
                org.jsoup.parser.HtmlTreeBuilderState r36 = InCaption
                boolean r36 = r35.equals(r36)
                if (r36 != 0) goto L_0x0848
                org.jsoup.parser.HtmlTreeBuilderState r36 = InTableBody
                boolean r36 = r35.equals(r36)
                if (r36 != 0) goto L_0x0848
                org.jsoup.parser.HtmlTreeBuilderState r36 = InRow
                boolean r36 = r35.equals(r36)
                if (r36 != 0) goto L_0x0848
                org.jsoup.parser.HtmlTreeBuilderState r36 = InCell
                boolean r36 = r35.equals(r36)
                if (r36 == 0) goto L_0x0853
            L_0x0848:
                org.jsoup.parser.HtmlTreeBuilderState r36 = InSelectInTable
                r0 = r41
                r1 = r36
                r0.transition(r1)
                goto L_0x0011
            L_0x0853:
                org.jsoup.parser.HtmlTreeBuilderState r36 = InSelect
                r0 = r41
                r1 = r36
                r0.transition(r1)
                goto L_0x0011
            L_0x085e:
                java.lang.String[] r36 = org.jsoup.parser.HtmlTreeBuilderState.Constants.InBodyStartOptions
                r0 = r23
                r1 = r36
                boolean r36 = org.jsoup.helper.StringUtil.inSorted(r0, r1)
                if (r36 == 0) goto L_0x0891
                org.jsoup.nodes.Element r36 = r41.currentElement()
                java.lang.String r36 = r36.nodeName()
                java.lang.String r37 = "option"
                boolean r36 = r36.equals(r37)
                if (r36 == 0) goto L_0x0885
                java.lang.String r36 = "option"
                r0 = r41
                r1 = r36
                r0.processEndTag(r1)
            L_0x0885:
                r41.reconstructFormattingElements()
                r0 = r41
                r1 = r34
                r0.insert((org.jsoup.parser.Token.StartTag) r1)
                goto L_0x0011
            L_0x0891:
                java.lang.String[] r36 = org.jsoup.parser.HtmlTreeBuilderState.Constants.InBodyStartRuby
                r0 = r23
                r1 = r36
                boolean r36 = org.jsoup.helper.StringUtil.inSorted(r0, r1)
                if (r36 == 0) goto L_0x08d7
                java.lang.String r36 = "ruby"
                r0 = r41
                r1 = r36
                boolean r36 = r0.inScope((java.lang.String) r1)
                if (r36 == 0) goto L_0x0011
                r41.generateImpliedEndTags()
                org.jsoup.nodes.Element r36 = r41.currentElement()
                java.lang.String r36 = r36.nodeName()
                java.lang.String r37 = "ruby"
                boolean r36 = r36.equals(r37)
                if (r36 != 0) goto L_0x08ce
                r0 = r41
                r1 = r39
                r0.error(r1)
                java.lang.String r36 = "ruby"
                r0 = r41
                r1 = r36
                r0.popStackToBefore(r1)
            L_0x08ce:
                r0 = r41
                r1 = r34
                r0.insert((org.jsoup.parser.Token.StartTag) r1)
                goto L_0x0011
            L_0x08d7:
                java.lang.String r36 = "math"
                r0 = r23
                r1 = r36
                boolean r36 = r0.equals(r1)
                if (r36 == 0) goto L_0x08f8
                r41.reconstructFormattingElements()
                r0 = r41
                r1 = r34
                r0.insert((org.jsoup.parser.Token.StartTag) r1)
                r0 = r41
                org.jsoup.parser.Tokeniser r0 = r0.tokeniser
                r36 = r0
                r36.acknowledgeSelfClosingFlag()
                goto L_0x0011
            L_0x08f8:
                java.lang.String r36 = "svg"
                r0 = r23
                r1 = r36
                boolean r36 = r0.equals(r1)
                if (r36 == 0) goto L_0x0919
                r41.reconstructFormattingElements()
                r0 = r41
                r1 = r34
                r0.insert((org.jsoup.parser.Token.StartTag) r1)
                r0 = r41
                org.jsoup.parser.Tokeniser r0 = r0.tokeniser
                r36 = r0
                r36.acknowledgeSelfClosingFlag()
                goto L_0x0011
            L_0x0919:
                java.lang.String[] r36 = org.jsoup.parser.HtmlTreeBuilderState.Constants.InBodyStartDrop
                r0 = r23
                r1 = r36
                boolean r36 = org.jsoup.helper.StringUtil.inSorted(r0, r1)
                if (r36 == 0) goto L_0x0932
                r0 = r41
                r1 = r39
                r0.error(r1)
                r36 = 0
                goto L_0x0013
            L_0x0932:
                r41.reconstructFormattingElements()
                r0 = r41
                r1 = r34
                r0.insert((org.jsoup.parser.Token.StartTag) r1)
                goto L_0x0011
            L_0x093e:
                org.jsoup.parser.Token$EndTag r14 = r40.asEndTag()
                java.lang.String r23 = r14.name()
                java.lang.String[] r36 = org.jsoup.parser.HtmlTreeBuilderState.Constants.InBodyEndAdoptionFormatters
                r0 = r23
                r1 = r36
                boolean r36 = org.jsoup.helper.StringUtil.inSorted(r0, r1)
                if (r36 == 0) goto L_0x0b22
                r19 = 0
            L_0x0956:
                r36 = 8
                r0 = r19
                r1 = r36
                if (r0 >= r1) goto L_0x0011
                r0 = r41
                r1 = r23
                org.jsoup.nodes.Element r16 = r0.getActiveFormattingElement(r1)
                if (r16 != 0) goto L_0x096e
                boolean r36 = r39.anyOtherEndTag(r40, r41)
                goto L_0x0013
            L_0x096e:
                r0 = r41
                r1 = r16
                boolean r36 = r0.onStack(r1)
                if (r36 != 0) goto L_0x098a
                r0 = r41
                r1 = r39
                r0.error(r1)
                r0 = r41
                r1 = r16
                r0.removeFromActiveFormattingElements(r1)
                r36 = 1
                goto L_0x0013
            L_0x098a:
                java.lang.String r36 = r16.nodeName()
                r0 = r41
                r1 = r36
                boolean r36 = r0.inScope((java.lang.String) r1)
                if (r36 != 0) goto L_0x09a3
                r0 = r41
                r1 = r39
                r0.error(r1)
                r36 = 0
                goto L_0x0013
            L_0x09a3:
                org.jsoup.nodes.Element r36 = r41.currentElement()
                r0 = r36
                r1 = r16
                if (r0 == r1) goto L_0x09b4
                r0 = r41
                r1 = r39
                r0.error(r1)
            L_0x09b4:
                r17 = 0
                r11 = 0
                r30 = 0
                java.util.ArrayList r32 = r41.getStack()
                int r33 = r32.size()
                r31 = 0
            L_0x09c3:
                r0 = r31
                r1 = r33
                if (r0 >= r1) goto L_0x09fc
                r36 = 64
                r0 = r31
                r1 = r36
                if (r0 >= r1) goto L_0x09fc
                r0 = r32
                r1 = r31
                java.lang.Object r13 = r0.get(r1)
                org.jsoup.nodes.Element r13 = (org.jsoup.nodes.Element) r13
                r0 = r16
                if (r13 != r0) goto L_0x09f0
                int r36 = r31 + -1
                r0 = r32
                r1 = r36
                java.lang.Object r11 = r0.get(r1)
                org.jsoup.nodes.Element r11 = (org.jsoup.nodes.Element) r11
                r30 = 1
            L_0x09ed:
                int r31 = r31 + 1
                goto L_0x09c3
            L_0x09f0:
                if (r30 == 0) goto L_0x09ed
                r0 = r41
                boolean r36 = r0.isSpecial(r13)
                if (r36 == 0) goto L_0x09ed
                r17 = r13
            L_0x09fc:
                if (r17 != 0) goto L_0x0a14
                java.lang.String r36 = r16.nodeName()
                r0 = r41
                r1 = r36
                r0.popStackToClose((java.lang.String) r1)
                r0 = r41
                r1 = r16
                r0.removeFromActiveFormattingElements(r1)
                r36 = 1
                goto L_0x0013
            L_0x0a14:
                r24 = r17
                r22 = r17
                r21 = 0
            L_0x0a1a:
                r36 = 3
                r0 = r21
                r1 = r36
                if (r0 >= r1) goto L_0x0a4e
                r0 = r41
                r1 = r24
                boolean r36 = r0.onStack(r1)
                if (r36 == 0) goto L_0x0a34
                r0 = r41
                r1 = r24
                org.jsoup.nodes.Element r24 = r0.aboveOnStack(r1)
            L_0x0a34:
                r0 = r41
                r1 = r24
                boolean r36 = r0.isInActiveFormattingElements(r1)
                if (r36 != 0) goto L_0x0a48
                r0 = r41
                r1 = r24
                r0.removeFromStack(r1)
            L_0x0a45:
                int r21 = r21 + 1
                goto L_0x0a1a
            L_0x0a48:
                r0 = r24
                r1 = r16
                if (r0 != r1) goto L_0x0aaf
            L_0x0a4e:
                java.lang.String r36 = r11.nodeName()
                java.lang.String[] r37 = org.jsoup.parser.HtmlTreeBuilderState.Constants.InBodyEndTableFosters
                boolean r36 = org.jsoup.helper.StringUtil.inSorted(r36, r37)
                if (r36 == 0) goto L_0x0af4
                org.jsoup.nodes.Element r36 = r22.parent()
                if (r36 == 0) goto L_0x0a65
                r22.remove()
            L_0x0a65:
                r0 = r41
                r1 = r22
                r0.insertInFosterParent(r1)
            L_0x0a6c:
                org.jsoup.nodes.Element r4 = new org.jsoup.nodes.Element
                org.jsoup.parser.Tag r36 = r16.tag()
                java.lang.String r37 = r41.getBaseUri()
                r0 = r36
                r1 = r37
                r4.<init>(r0, r1)
                org.jsoup.nodes.Attributes r36 = r4.attributes()
                org.jsoup.nodes.Attributes r37 = r16.attributes()
                r36.addAll(r37)
                java.util.List r36 = r17.childNodes()
                int r37 = r17.childNodeSize()
                r0 = r37
                org.jsoup.nodes.Node[] r0 = new org.jsoup.nodes.Node[r0]
                r37 = r0
                java.lang.Object[] r10 = r36.toArray(r37)
                org.jsoup.nodes.Node[] r10 = (org.jsoup.nodes.Node[]) r10
                int r0 = r10.length
                r37 = r0
                r36 = 0
            L_0x0aa1:
                r0 = r36
                r1 = r37
                if (r0 >= r1) goto L_0x0b04
                r9 = r10[r36]
                r4.appendChild(r9)
                int r36 = r36 + 1
                goto L_0x0aa1
            L_0x0aaf:
                org.jsoup.nodes.Element r28 = new org.jsoup.nodes.Element
                java.lang.String r36 = r24.nodeName()
                org.jsoup.parser.Tag r36 = org.jsoup.parser.Tag.valueOf(r36)
                java.lang.String r37 = r41.getBaseUri()
                r0 = r28
                r1 = r36
                r2 = r37
                r0.<init>(r1, r2)
                r0 = r41
                r1 = r24
                r2 = r28
                r0.replaceActiveFormattingElement(r1, r2)
                r0 = r41
                r1 = r24
                r2 = r28
                r0.replaceOnStack(r1, r2)
                r24 = r28
                r0 = r22
                r1 = r17
                if (r0 != r1) goto L_0x0ae0
            L_0x0ae0:
                org.jsoup.nodes.Element r36 = r22.parent()
                if (r36 == 0) goto L_0x0ae9
                r22.remove()
            L_0x0ae9:
                r0 = r24
                r1 = r22
                r0.appendChild(r1)
                r22 = r24
                goto L_0x0a45
            L_0x0af4:
                org.jsoup.nodes.Element r36 = r22.parent()
                if (r36 == 0) goto L_0x0afd
                r22.remove()
            L_0x0afd:
                r0 = r22
                r11.appendChild(r0)
                goto L_0x0a6c
            L_0x0b04:
                r0 = r17
                r0.appendChild(r4)
                r0 = r41
                r1 = r16
                r0.removeFromActiveFormattingElements(r1)
                r0 = r41
                r1 = r16
                r0.removeFromStack(r1)
                r0 = r41
                r1 = r17
                r0.insertOnStackAfter(r1, r4)
                int r19 = r19 + 1
                goto L_0x0956
            L_0x0b22:
                java.lang.String[] r36 = org.jsoup.parser.HtmlTreeBuilderState.Constants.InBodyEndClosers
                r0 = r23
                r1 = r36
                boolean r36 = org.jsoup.helper.StringUtil.inSorted(r0, r1)
                if (r36 == 0) goto L_0x0b6a
                r0 = r41
                r1 = r23
                boolean r36 = r0.inScope((java.lang.String) r1)
                if (r36 != 0) goto L_0x0b45
                r0 = r41
                r1 = r39
                r0.error(r1)
                r36 = 0
                goto L_0x0013
            L_0x0b45:
                r41.generateImpliedEndTags()
                org.jsoup.nodes.Element r36 = r41.currentElement()
                java.lang.String r36 = r36.nodeName()
                r0 = r36
                r1 = r23
                boolean r36 = r0.equals(r1)
                if (r36 != 0) goto L_0x0b61
                r0 = r41
                r1 = r39
                r0.error(r1)
            L_0x0b61:
                r0 = r41
                r1 = r23
                r0.popStackToClose((java.lang.String) r1)
                goto L_0x0011
            L_0x0b6a:
                java.lang.String r36 = "span"
                r0 = r23
                r1 = r36
                boolean r36 = r0.equals(r1)
                if (r36 == 0) goto L_0x0b7c
                boolean r36 = r39.anyOtherEndTag(r40, r41)
                goto L_0x0013
            L_0x0b7c:
                java.lang.String r36 = "li"
                r0 = r23
                r1 = r36
                boolean r36 = r0.equals(r1)
                if (r36 == 0) goto L_0x0bc6
                r0 = r41
                r1 = r23
                boolean r36 = r0.inListItemScope(r1)
                if (r36 != 0) goto L_0x0b9d
                r0 = r41
                r1 = r39
                r0.error(r1)
                r36 = 0
                goto L_0x0013
            L_0x0b9d:
                r0 = r41
                r1 = r23
                r0.generateImpliedEndTags(r1)
                org.jsoup.nodes.Element r36 = r41.currentElement()
                java.lang.String r36 = r36.nodeName()
                r0 = r36
                r1 = r23
                boolean r36 = r0.equals(r1)
                if (r36 != 0) goto L_0x0bbd
                r0 = r41
                r1 = r39
                r0.error(r1)
            L_0x0bbd:
                r0 = r41
                r1 = r23
                r0.popStackToClose((java.lang.String) r1)
                goto L_0x0011
            L_0x0bc6:
                java.lang.String r36 = "body"
                r0 = r23
                r1 = r36
                boolean r36 = r0.equals(r1)
                if (r36 == 0) goto L_0x0bf4
                java.lang.String r36 = "body"
                r0 = r41
                r1 = r36
                boolean r36 = r0.inScope((java.lang.String) r1)
                if (r36 != 0) goto L_0x0be9
                r0 = r41
                r1 = r39
                r0.error(r1)
                r36 = 0
                goto L_0x0013
            L_0x0be9:
                org.jsoup.parser.HtmlTreeBuilderState r36 = AfterBody
                r0 = r41
                r1 = r36
                r0.transition(r1)
                goto L_0x0011
            L_0x0bf4:
                java.lang.String r36 = "html"
                r0 = r23
                r1 = r36
                boolean r36 = r0.equals(r1)
                if (r36 == 0) goto L_0x0c14
                java.lang.String r36 = "body"
                r0 = r41
                r1 = r36
                boolean r25 = r0.processEndTag(r1)
                if (r25 == 0) goto L_0x0011
                r0 = r41
                boolean r36 = r0.process(r14)
                goto L_0x0013
            L_0x0c14:
                java.lang.String r36 = "form"
                r0 = r23
                r1 = r36
                boolean r36 = r0.equals(r1)
                if (r36 == 0) goto L_0x0c67
                org.jsoup.nodes.FormElement r12 = r41.getFormElement()
                r36 = 0
                r0 = r41
                r1 = r36
                r0.setFormElement(r1)
                if (r12 == 0) goto L_0x0c39
                r0 = r41
                r1 = r23
                boolean r36 = r0.inScope((java.lang.String) r1)
                if (r36 != 0) goto L_0x0c44
            L_0x0c39:
                r0 = r41
                r1 = r39
                r0.error(r1)
                r36 = 0
                goto L_0x0013
            L_0x0c44:
                r41.generateImpliedEndTags()
                org.jsoup.nodes.Element r36 = r41.currentElement()
                java.lang.String r36 = r36.nodeName()
                r0 = r36
                r1 = r23
                boolean r36 = r0.equals(r1)
                if (r36 != 0) goto L_0x0c60
                r0 = r41
                r1 = r39
                r0.error(r1)
            L_0x0c60:
                r0 = r41
                r0.removeFromStack(r12)
                goto L_0x0011
            L_0x0c67:
                java.lang.String r36 = "p"
                r0 = r23
                r1 = r36
                boolean r36 = r0.equals(r1)
                if (r36 == 0) goto L_0x0cbc
                r0 = r41
                r1 = r23
                boolean r36 = r0.inButtonScope(r1)
                if (r36 != 0) goto L_0x0c93
                r0 = r41
                r1 = r39
                r0.error(r1)
                r0 = r41
                r1 = r23
                r0.processStartTag(r1)
                r0 = r41
                boolean r36 = r0.process(r14)
                goto L_0x0013
            L_0x0c93:
                r0 = r41
                r1 = r23
                r0.generateImpliedEndTags(r1)
                org.jsoup.nodes.Element r36 = r41.currentElement()
                java.lang.String r36 = r36.nodeName()
                r0 = r36
                r1 = r23
                boolean r36 = r0.equals(r1)
                if (r36 != 0) goto L_0x0cb3
                r0 = r41
                r1 = r39
                r0.error(r1)
            L_0x0cb3:
                r0 = r41
                r1 = r23
                r0.popStackToClose((java.lang.String) r1)
                goto L_0x0011
            L_0x0cbc:
                java.lang.String[] r36 = org.jsoup.parser.HtmlTreeBuilderState.Constants.DdDt
                r0 = r23
                r1 = r36
                boolean r36 = org.jsoup.helper.StringUtil.inSorted(r0, r1)
                if (r36 == 0) goto L_0x0d08
                r0 = r41
                r1 = r23
                boolean r36 = r0.inScope((java.lang.String) r1)
                if (r36 != 0) goto L_0x0cdf
                r0 = r41
                r1 = r39
                r0.error(r1)
                r36 = 0
                goto L_0x0013
            L_0x0cdf:
                r0 = r41
                r1 = r23
                r0.generateImpliedEndTags(r1)
                org.jsoup.nodes.Element r36 = r41.currentElement()
                java.lang.String r36 = r36.nodeName()
                r0 = r36
                r1 = r23
                boolean r36 = r0.equals(r1)
                if (r36 != 0) goto L_0x0cff
                r0 = r41
                r1 = r39
                r0.error(r1)
            L_0x0cff:
                r0 = r41
                r1 = r23
                r0.popStackToClose((java.lang.String) r1)
                goto L_0x0011
            L_0x0d08:
                java.lang.String[] r36 = org.jsoup.parser.HtmlTreeBuilderState.Constants.Headings
                r0 = r23
                r1 = r36
                boolean r36 = org.jsoup.helper.StringUtil.inSorted(r0, r1)
                if (r36 == 0) goto L_0x0d5c
                java.lang.String[] r36 = org.jsoup.parser.HtmlTreeBuilderState.Constants.Headings
                r0 = r41
                r1 = r36
                boolean r36 = r0.inScope((java.lang.String[]) r1)
                if (r36 != 0) goto L_0x0d2f
                r0 = r41
                r1 = r39
                r0.error(r1)
                r36 = 0
                goto L_0x0013
            L_0x0d2f:
                r0 = r41
                r1 = r23
                r0.generateImpliedEndTags(r1)
                org.jsoup.nodes.Element r36 = r41.currentElement()
                java.lang.String r36 = r36.nodeName()
                r0 = r36
                r1 = r23
                boolean r36 = r0.equals(r1)
                if (r36 != 0) goto L_0x0d4f
                r0 = r41
                r1 = r39
                r0.error(r1)
            L_0x0d4f:
                java.lang.String[] r36 = org.jsoup.parser.HtmlTreeBuilderState.Constants.Headings
                r0 = r41
                r1 = r36
                r0.popStackToClose((java.lang.String[]) r1)
                goto L_0x0011
            L_0x0d5c:
                java.lang.String r36 = "sarcasm"
                r0 = r23
                r1 = r36
                boolean r36 = r0.equals(r1)
                if (r36 == 0) goto L_0x0d6e
                boolean r36 = r39.anyOtherEndTag(r40, r41)
                goto L_0x0013
            L_0x0d6e:
                java.lang.String[] r36 = org.jsoup.parser.HtmlTreeBuilderState.Constants.InBodyStartApplets
                r0 = r23
                r1 = r36
                boolean r36 = org.jsoup.helper.StringUtil.inSorted(r0, r1)
                if (r36 == 0) goto L_0x0dc5
                java.lang.String r36 = "name"
                r0 = r41
                r1 = r36
                boolean r36 = r0.inScope((java.lang.String) r1)
                if (r36 != 0) goto L_0x0011
                r0 = r41
                r1 = r23
                boolean r36 = r0.inScope((java.lang.String) r1)
                if (r36 != 0) goto L_0x0d9d
                r0 = r41
                r1 = r39
                r0.error(r1)
                r36 = 0
                goto L_0x0013
            L_0x0d9d:
                r41.generateImpliedEndTags()
                org.jsoup.nodes.Element r36 = r41.currentElement()
                java.lang.String r36 = r36.nodeName()
                r0 = r36
                r1 = r23
                boolean r36 = r0.equals(r1)
                if (r36 != 0) goto L_0x0db9
                r0 = r41
                r1 = r39
                r0.error(r1)
            L_0x0db9:
                r0 = r41
                r1 = r23
                r0.popStackToClose((java.lang.String) r1)
                r41.clearFormattingElementsToLastMarker()
                goto L_0x0011
            L_0x0dc5:
                java.lang.String r36 = "br"
                r0 = r23
                r1 = r36
                boolean r36 = r0.equals(r1)
                if (r36 == 0) goto L_0x0de5
                r0 = r41
                r1 = r39
                r0.error(r1)
                java.lang.String r36 = "br"
                r0 = r41
                r1 = r36
                r0.processStartTag(r1)
                r36 = 0
                goto L_0x0013
            L_0x0de5:
                boolean r36 = r39.anyOtherEndTag(r40, r41)
                goto L_0x0013
            */
            throw new UnsupportedOperationException("Method not decompiled: org.jsoup.parser.HtmlTreeBuilderState.AnonymousClass7.process(org.jsoup.parser.Token, org.jsoup.parser.HtmlTreeBuilder):boolean");
        }

        /* access modifiers changed from: package-private */
        public boolean anyOtherEndTag(Token t, HtmlTreeBuilder tb) {
            String name = t.asEndTag().name();
            ArrayList<Element> stack = tb.getStack();
            int pos = stack.size() - 1;
            while (true) {
                if (pos < 0) {
                    break;
                }
                Element node = stack.get(pos);
                if (node.nodeName().equals(name)) {
                    tb.generateImpliedEndTags(name);
                    if (!name.equals(tb.currentElement().nodeName())) {
                        tb.error(this);
                    }
                    tb.popStackToClose(name);
                } else if (tb.isSpecial(node)) {
                    tb.error(this);
                    return false;
                } else {
                    pos--;
                }
            }
            return true;
        }
    },
    Text {
        /* access modifiers changed from: package-private */
        public boolean process(Token t, HtmlTreeBuilder tb) {
            if (t.isCharacter()) {
                tb.insert(t.asCharacter());
            } else if (t.isEOF()) {
                tb.error(this);
                tb.pop();
                tb.transition(tb.originalState());
                return tb.process(t);
            } else if (t.isEndTag()) {
                tb.pop();
                tb.transition(tb.originalState());
            }
            return true;
        }
    },
    InTable {
        /* access modifiers changed from: package-private */
        public boolean process(Token t, HtmlTreeBuilder tb) {
            if (t.isCharacter()) {
                tb.newPendingTableCharacters();
                tb.markInsertionMode();
                tb.transition(InTableText);
                return tb.process(t);
            } else if (t.isComment()) {
                tb.insert(t.asComment());
                return true;
            } else if (t.isDoctype()) {
                tb.error(this);
                return false;
            } else if (t.isStartTag()) {
                Token.StartTag startTag = t.asStartTag();
                String name = startTag.name();
                if (name.equals(ShareConstants.FEED_CAPTION_PARAM)) {
                    tb.clearStackToTableContext();
                    tb.insertMarkerToFormattingElements();
                    tb.insert(startTag);
                    tb.transition(InCaption);
                    return true;
                } else if (name.equals("colgroup")) {
                    tb.clearStackToTableContext();
                    tb.insert(startTag);
                    tb.transition(InColumnGroup);
                    return true;
                } else if (name.equals("col")) {
                    tb.processStartTag("colgroup");
                    return tb.process(t);
                } else {
                    if (StringUtil.in(name, "tbody", "tfoot", "thead")) {
                        tb.clearStackToTableContext();
                        tb.insert(startTag);
                        tb.transition(InTableBody);
                        return true;
                    }
                    if (StringUtil.in(name, "td", "th", "tr")) {
                        tb.processStartTag("tbody");
                        return tb.process(t);
                    } else if (name.equals("table")) {
                        tb.error(this);
                        if (tb.processEndTag("table")) {
                            return tb.process(t);
                        }
                        return true;
                    } else {
                        if (StringUtil.in(name, AnalyticsEvents.PARAMETER_LIKE_VIEW_STYLE, "script")) {
                            return tb.process(t, InHead);
                        }
                        if (name.equals("input")) {
                            if (!startTag.attributes.get("type").equalsIgnoreCase("hidden")) {
                                return anythingElse(t, tb);
                            }
                            tb.insertEmpty(startTag);
                            return true;
                        } else if (!name.equals("form")) {
                            return anythingElse(t, tb);
                        } else {
                            tb.error(this);
                            if (tb.getFormElement() != null) {
                                return false;
                            }
                            tb.insertForm(startTag, false);
                            return true;
                        }
                    }
                }
            } else if (t.isEndTag()) {
                String name2 = t.asEndTag().name();
                if (!name2.equals("table")) {
                    if (!StringUtil.in(name2, "body", ShareConstants.FEED_CAPTION_PARAM, "col", "colgroup", ContentType.SUBTYPE_HTML, "tbody", "td", "tfoot", "th", "thead", "tr")) {
                        return anythingElse(t, tb);
                    }
                    tb.error(this);
                    return false;
                } else if (!tb.inTableScope(name2)) {
                    tb.error(this);
                    return false;
                } else {
                    tb.popStackToClose("table");
                    tb.resetInsertionMode();
                    return true;
                }
            } else if (!t.isEOF()) {
                return anythingElse(t, tb);
            } else {
                if (!tb.currentElement().nodeName().equals(ContentType.SUBTYPE_HTML)) {
                    return true;
                }
                tb.error(this);
                return true;
            }
        }

        /* access modifiers changed from: package-private */
        public boolean anythingElse(Token t, HtmlTreeBuilder tb) {
            tb.error(this);
            if (!StringUtil.in(tb.currentElement().nodeName(), "table", "tbody", "tfoot", "thead", "tr")) {
                return tb.process(t, InBody);
            }
            tb.setFosterInserts(true);
            boolean processed = tb.process(t, InBody);
            tb.setFosterInserts(false);
            return processed;
        }
    },
    InTableText {
        /* access modifiers changed from: package-private */
        public boolean process(Token t, HtmlTreeBuilder tb) {
            switch (AnonymousClass24.$SwitchMap$org$jsoup$parser$Token$TokenType[t.type.ordinal()]) {
                case 5:
                    Token.Character c = t.asCharacter();
                    if (c.getData().equals(HtmlTreeBuilderState.nullString)) {
                        tb.error(this);
                        return false;
                    }
                    tb.getPendingTableCharacters().add(c.getData());
                    return true;
                default:
                    if (tb.getPendingTableCharacters().size() > 0) {
                        for (String character : tb.getPendingTableCharacters()) {
                            if (!HtmlTreeBuilderState.isWhitespace(character)) {
                                tb.error(this);
                                if (StringUtil.in(tb.currentElement().nodeName(), "table", "tbody", "tfoot", "thead", "tr")) {
                                    tb.setFosterInserts(true);
                                    tb.process(new Token.Character().data(character), InBody);
                                    tb.setFosterInserts(false);
                                } else {
                                    tb.process(new Token.Character().data(character), InBody);
                                }
                            } else {
                                tb.insert(new Token.Character().data(character));
                            }
                        }
                        tb.newPendingTableCharacters();
                    }
                    tb.transition(tb.originalState());
                    return tb.process(t);
            }
        }
    },
    InCaption {
        /* access modifiers changed from: package-private */
        /* JADX WARNING: Code restructure failed: missing block: B:15:0x0091, code lost:
            if (org.jsoup.helper.StringUtil.in(r13.asStartTag().name(), com.facebook.share.internal.ShareConstants.FEED_CAPTION_PARAM, "col", "colgroup", "tbody", "td", "tfoot", "th", "thead", "tr") == false) goto L_0x0093;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean process(org.jsoup.parser.Token r13, org.jsoup.parser.HtmlTreeBuilder r14) {
            /*
                r12 = this;
                r11 = 4
                r10 = 3
                r9 = 2
                r4 = 1
                r3 = 0
                boolean r5 = r13.isEndTag()
                if (r5 == 0) goto L_0x0052
                org.jsoup.parser.Token$EndTag r5 = r13.asEndTag()
                java.lang.String r5 = r5.name()
                java.lang.String r6 = "caption"
                boolean r5 = r5.equals(r6)
                if (r5 == 0) goto L_0x0052
                org.jsoup.parser.Token$EndTag r0 = r13.asEndTag()
                java.lang.String r1 = r0.name()
                boolean r5 = r14.inTableScope(r1)
                if (r5 != 0) goto L_0x002d
                r14.error(r12)
            L_0x002c:
                return r3
            L_0x002d:
                r14.generateImpliedEndTags()
                org.jsoup.nodes.Element r3 = r14.currentElement()
                java.lang.String r3 = r3.nodeName()
                java.lang.String r5 = "caption"
                boolean r3 = r3.equals(r5)
                if (r3 != 0) goto L_0x0043
                r14.error(r12)
            L_0x0043:
                java.lang.String r3 = "caption"
                r14.popStackToClose((java.lang.String) r3)
                r14.clearFormattingElementsToLastMarker()
                org.jsoup.parser.HtmlTreeBuilderState r3 = InTable
                r14.transition(r3)
            L_0x0050:
                r3 = r4
                goto L_0x002c
            L_0x0052:
                boolean r5 = r13.isStartTag()
                if (r5 == 0) goto L_0x0093
                org.jsoup.parser.Token$StartTag r5 = r13.asStartTag()
                java.lang.String r5 = r5.name()
                r6 = 9
                java.lang.String[] r6 = new java.lang.String[r6]
                java.lang.String r7 = "caption"
                r6[r3] = r7
                java.lang.String r7 = "col"
                r6[r4] = r7
                java.lang.String r7 = "colgroup"
                r6[r9] = r7
                java.lang.String r7 = "tbody"
                r6[r10] = r7
                java.lang.String r7 = "td"
                r6[r11] = r7
                r7 = 5
                java.lang.String r8 = "tfoot"
                r6[r7] = r8
                r7 = 6
                java.lang.String r8 = "th"
                r6[r7] = r8
                r7 = 7
                java.lang.String r8 = "thead"
                r6[r7] = r8
                r7 = 8
                java.lang.String r8 = "tr"
                r6[r7] = r8
                boolean r5 = org.jsoup.helper.StringUtil.in(r5, r6)
                if (r5 != 0) goto L_0x00a9
            L_0x0093:
                boolean r5 = r13.isEndTag()
                if (r5 == 0) goto L_0x00ba
                org.jsoup.parser.Token$EndTag r5 = r13.asEndTag()
                java.lang.String r5 = r5.name()
                java.lang.String r6 = "table"
                boolean r5 = r5.equals(r6)
                if (r5 == 0) goto L_0x00ba
            L_0x00a9:
                r14.error(r12)
                java.lang.String r3 = "caption"
                boolean r2 = r14.processEndTag(r3)
                if (r2 == 0) goto L_0x0050
                boolean r3 = r14.process(r13)
                goto L_0x002c
            L_0x00ba:
                boolean r5 = r13.isEndTag()
                if (r5 == 0) goto L_0x0106
                org.jsoup.parser.Token$EndTag r5 = r13.asEndTag()
                java.lang.String r5 = r5.name()
                r6 = 10
                java.lang.String[] r6 = new java.lang.String[r6]
                java.lang.String r7 = "body"
                r6[r3] = r7
                java.lang.String r7 = "col"
                r6[r4] = r7
                java.lang.String r4 = "colgroup"
                r6[r9] = r4
                java.lang.String r4 = "html"
                r6[r10] = r4
                java.lang.String r4 = "tbody"
                r6[r11] = r4
                r4 = 5
                java.lang.String r7 = "td"
                r6[r4] = r7
                r4 = 6
                java.lang.String r7 = "tfoot"
                r6[r4] = r7
                r4 = 7
                java.lang.String r7 = "th"
                r6[r4] = r7
                r4 = 8
                java.lang.String r7 = "thead"
                r6[r4] = r7
                r4 = 9
                java.lang.String r7 = "tr"
                r6[r4] = r7
                boolean r4 = org.jsoup.helper.StringUtil.in(r5, r6)
                if (r4 == 0) goto L_0x0106
                r14.error(r12)
                goto L_0x002c
            L_0x0106:
                org.jsoup.parser.HtmlTreeBuilderState r3 = InBody
                boolean r3 = r14.process(r13, r3)
                goto L_0x002c
            */
            throw new UnsupportedOperationException("Method not decompiled: org.jsoup.parser.HtmlTreeBuilderState.AnonymousClass11.process(org.jsoup.parser.Token, org.jsoup.parser.HtmlTreeBuilder):boolean");
        }
    },
    InColumnGroup {
        /* access modifiers changed from: package-private */
        public boolean process(Token t, HtmlTreeBuilder tb) {
            if (HtmlTreeBuilderState.isWhitespace(t)) {
                tb.insert(t.asCharacter());
                return true;
            }
            switch (AnonymousClass24.$SwitchMap$org$jsoup$parser$Token$TokenType[t.type.ordinal()]) {
                case 1:
                    tb.insert(t.asComment());
                    return true;
                case 2:
                    tb.error(this);
                    return true;
                case 3:
                    Token.StartTag startTag = t.asStartTag();
                    String name = startTag.name();
                    if (name.equals(ContentType.SUBTYPE_HTML)) {
                        return tb.process(t, InBody);
                    }
                    if (!name.equals("col")) {
                        return anythingElse(t, tb);
                    }
                    tb.insertEmpty(startTag);
                    return true;
                case 4:
                    if (!t.asEndTag().name().equals("colgroup")) {
                        return anythingElse(t, tb);
                    }
                    if (tb.currentElement().nodeName().equals(ContentType.SUBTYPE_HTML)) {
                        tb.error(this);
                        return false;
                    }
                    tb.pop();
                    tb.transition(InTable);
                    return true;
                case 6:
                    if (!tb.currentElement().nodeName().equals(ContentType.SUBTYPE_HTML)) {
                        return anythingElse(t, tb);
                    }
                    return true;
                default:
                    return anythingElse(t, tb);
            }
        }

        private boolean anythingElse(Token t, TreeBuilder tb) {
            if (tb.processEndTag("colgroup")) {
                return tb.process(t);
            }
            return true;
        }
    },
    InTableBody {
        /* access modifiers changed from: package-private */
        public boolean process(Token t, HtmlTreeBuilder tb) {
            switch (AnonymousClass24.$SwitchMap$org$jsoup$parser$Token$TokenType[t.type.ordinal()]) {
                case 3:
                    Token.StartTag startTag = t.asStartTag();
                    String name = startTag.name();
                    if (name.equals("tr")) {
                        tb.clearStackToTableBodyContext();
                        tb.insert(startTag);
                        tb.transition(InRow);
                        break;
                    } else {
                        if (StringUtil.in(name, "th", "td")) {
                            tb.error(this);
                            tb.processStartTag("tr");
                            return tb.process(startTag);
                        }
                        if (StringUtil.in(name, ShareConstants.FEED_CAPTION_PARAM, "col", "colgroup", "tbody", "tfoot", "thead")) {
                            return exitTableBody(t, tb);
                        }
                        return anythingElse(t, tb);
                    }
                case 4:
                    String name2 = t.asEndTag().name();
                    if (StringUtil.in(name2, "tbody", "tfoot", "thead")) {
                        if (tb.inTableScope(name2)) {
                            tb.clearStackToTableBodyContext();
                            tb.pop();
                            tb.transition(InTable);
                            break;
                        } else {
                            tb.error(this);
                            return false;
                        }
                    } else if (name2.equals("table")) {
                        return exitTableBody(t, tb);
                    } else {
                        if (!StringUtil.in(name2, "body", ShareConstants.FEED_CAPTION_PARAM, "col", "colgroup", ContentType.SUBTYPE_HTML, "td", "th", "tr")) {
                            return anythingElse(t, tb);
                        }
                        tb.error(this);
                        return false;
                    }
                default:
                    return anythingElse(t, tb);
            }
            return true;
        }

        private boolean exitTableBody(Token t, HtmlTreeBuilder tb) {
            if (tb.inTableScope("tbody") || tb.inTableScope("thead") || tb.inScope("tfoot")) {
                tb.clearStackToTableBodyContext();
                tb.processEndTag(tb.currentElement().nodeName());
                return tb.process(t);
            }
            tb.error(this);
            return false;
        }

        private boolean anythingElse(Token t, HtmlTreeBuilder tb) {
            return tb.process(t, InTable);
        }
    },
    InRow {
        /* access modifiers changed from: package-private */
        public boolean process(Token t, HtmlTreeBuilder tb) {
            if (t.isStartTag()) {
                Token.StartTag startTag = t.asStartTag();
                String name = startTag.name();
                if (StringUtil.in(name, "th", "td")) {
                    tb.clearStackToTableRowContext();
                    tb.insert(startTag);
                    tb.transition(InCell);
                    tb.insertMarkerToFormattingElements();
                } else {
                    if (StringUtil.in(name, ShareConstants.FEED_CAPTION_PARAM, "col", "colgroup", "tbody", "tfoot", "thead", "tr")) {
                        return handleMissingTr(t, tb);
                    }
                    return anythingElse(t, tb);
                }
            } else if (!t.isEndTag()) {
                return anythingElse(t, tb);
            } else {
                String name2 = t.asEndTag().name();
                if (name2.equals("tr")) {
                    if (!tb.inTableScope(name2)) {
                        tb.error(this);
                        return false;
                    }
                    tb.clearStackToTableRowContext();
                    tb.pop();
                    tb.transition(InTableBody);
                } else if (name2.equals("table")) {
                    return handleMissingTr(t, tb);
                } else {
                    if (!StringUtil.in(name2, "tbody", "tfoot", "thead")) {
                        if (!StringUtil.in(name2, "body", ShareConstants.FEED_CAPTION_PARAM, "col", "colgroup", ContentType.SUBTYPE_HTML, "td", "th")) {
                            return anythingElse(t, tb);
                        }
                        tb.error(this);
                        return false;
                    } else if (!tb.inTableScope(name2)) {
                        tb.error(this);
                        return false;
                    } else {
                        tb.processEndTag("tr");
                        return tb.process(t);
                    }
                }
            }
            return true;
        }

        private boolean anythingElse(Token t, HtmlTreeBuilder tb) {
            return tb.process(t, InTable);
        }

        private boolean handleMissingTr(Token t, TreeBuilder tb) {
            if (tb.processEndTag("tr")) {
                return tb.process(t);
            }
            return false;
        }
    },
    InCell {
        /* access modifiers changed from: package-private */
        public boolean process(Token t, HtmlTreeBuilder tb) {
            if (t.isEndTag()) {
                String name = t.asEndTag().name();
                if (!StringUtil.in(name, "td", "th")) {
                    if (StringUtil.in(name, "body", ShareConstants.FEED_CAPTION_PARAM, "col", "colgroup", ContentType.SUBTYPE_HTML)) {
                        tb.error(this);
                        return false;
                    }
                    if (!StringUtil.in(name, "table", "tbody", "tfoot", "thead", "tr")) {
                        return anythingElse(t, tb);
                    }
                    if (!tb.inTableScope(name)) {
                        tb.error(this);
                        return false;
                    }
                    closeCell(tb);
                    return tb.process(t);
                } else if (!tb.inTableScope(name)) {
                    tb.error(this);
                    tb.transition(InRow);
                    return false;
                } else {
                    tb.generateImpliedEndTags();
                    if (!tb.currentElement().nodeName().equals(name)) {
                        tb.error(this);
                    }
                    tb.popStackToClose(name);
                    tb.clearFormattingElementsToLastMarker();
                    tb.transition(InRow);
                    return true;
                }
            } else {
                if (t.isStartTag()) {
                    if (StringUtil.in(t.asStartTag().name(), ShareConstants.FEED_CAPTION_PARAM, "col", "colgroup", "tbody", "td", "tfoot", "th", "thead", "tr")) {
                        if (tb.inTableScope("td") || tb.inTableScope("th")) {
                            closeCell(tb);
                            return tb.process(t);
                        }
                        tb.error(this);
                        return false;
                    }
                }
                return anythingElse(t, tb);
            }
        }

        private boolean anythingElse(Token t, HtmlTreeBuilder tb) {
            return tb.process(t, InBody);
        }

        private void closeCell(HtmlTreeBuilder tb) {
            if (tb.inTableScope("td")) {
                tb.processEndTag("td");
            } else {
                tb.processEndTag("th");
            }
        }
    },
    InSelect {
        /* access modifiers changed from: package-private */
        public boolean process(Token t, HtmlTreeBuilder tb) {
            switch (AnonymousClass24.$SwitchMap$org$jsoup$parser$Token$TokenType[t.type.ordinal()]) {
                case 1:
                    tb.insert(t.asComment());
                    break;
                case 2:
                    tb.error(this);
                    return false;
                case 3:
                    Token.StartTag start = t.asStartTag();
                    String name = start.name();
                    if (name.equals(ContentType.SUBTYPE_HTML)) {
                        return tb.process(start, InBody);
                    }
                    if (name.equals("option")) {
                        tb.processEndTag("option");
                        tb.insert(start);
                        break;
                    } else if (name.equals("optgroup")) {
                        if (tb.currentElement().nodeName().equals("option")) {
                            tb.processEndTag("option");
                        } else if (tb.currentElement().nodeName().equals("optgroup")) {
                            tb.processEndTag("optgroup");
                        }
                        tb.insert(start);
                        break;
                    } else if (name.equals("select")) {
                        tb.error(this);
                        return tb.processEndTag("select");
                    } else {
                        if (StringUtil.in(name, "input", "keygen", "textarea")) {
                            tb.error(this);
                            if (!tb.inSelectScope("select")) {
                                return false;
                            }
                            tb.processEndTag("select");
                            return tb.process(start);
                        } else if (name.equals("script")) {
                            return tb.process(t, InHead);
                        } else {
                            return anythingElse(t, tb);
                        }
                    }
                case 4:
                    String name2 = t.asEndTag().name();
                    if (name2.equals("optgroup")) {
                        if (tb.currentElement().nodeName().equals("option") && tb.aboveOnStack(tb.currentElement()) != null && tb.aboveOnStack(tb.currentElement()).nodeName().equals("optgroup")) {
                            tb.processEndTag("option");
                        }
                        if (!tb.currentElement().nodeName().equals("optgroup")) {
                            tb.error(this);
                            break;
                        } else {
                            tb.pop();
                            break;
                        }
                    } else if (name2.equals("option")) {
                        if (!tb.currentElement().nodeName().equals("option")) {
                            tb.error(this);
                            break;
                        } else {
                            tb.pop();
                            break;
                        }
                    } else if (name2.equals("select")) {
                        if (tb.inSelectScope(name2)) {
                            tb.popStackToClose(name2);
                            tb.resetInsertionMode();
                            break;
                        } else {
                            tb.error(this);
                            return false;
                        }
                    } else {
                        return anythingElse(t, tb);
                    }
                case 5:
                    Token.Character c = t.asCharacter();
                    if (!c.getData().equals(HtmlTreeBuilderState.nullString)) {
                        tb.insert(c);
                        break;
                    } else {
                        tb.error(this);
                        return false;
                    }
                case 6:
                    if (!tb.currentElement().nodeName().equals(ContentType.SUBTYPE_HTML)) {
                        tb.error(this);
                        break;
                    }
                    break;
                default:
                    return anythingElse(t, tb);
            }
            return true;
        }

        private boolean anythingElse(Token t, HtmlTreeBuilder tb) {
            tb.error(this);
            return false;
        }
    },
    InSelectInTable {
        /* access modifiers changed from: package-private */
        public boolean process(Token t, HtmlTreeBuilder tb) {
            if (t.isStartTag()) {
                if (StringUtil.in(t.asStartTag().name(), ShareConstants.FEED_CAPTION_PARAM, "table", "tbody", "tfoot", "thead", "tr", "td", "th")) {
                    tb.error(this);
                    tb.processEndTag("select");
                    return tb.process(t);
                }
            }
            if (t.isEndTag()) {
                if (StringUtil.in(t.asEndTag().name(), ShareConstants.FEED_CAPTION_PARAM, "table", "tbody", "tfoot", "thead", "tr", "td", "th")) {
                    tb.error(this);
                    if (!tb.inTableScope(t.asEndTag().name())) {
                        return false;
                    }
                    tb.processEndTag("select");
                    return tb.process(t);
                }
            }
            return tb.process(t, InSelect);
        }
    },
    AfterBody {
        /* access modifiers changed from: package-private */
        public boolean process(Token t, HtmlTreeBuilder tb) {
            if (HtmlTreeBuilderState.isWhitespace(t)) {
                return tb.process(t, InBody);
            }
            if (t.isComment()) {
                tb.insert(t.asComment());
            } else if (t.isDoctype()) {
                tb.error(this);
                return false;
            } else if (t.isStartTag() && t.asStartTag().name().equals(ContentType.SUBTYPE_HTML)) {
                return tb.process(t, InBody);
            } else {
                if (!t.isEndTag() || !t.asEndTag().name().equals(ContentType.SUBTYPE_HTML)) {
                    if (!t.isEOF()) {
                        tb.error(this);
                        tb.transition(InBody);
                        return tb.process(t);
                    }
                } else if (tb.isFragmentParsing()) {
                    tb.error(this);
                    return false;
                } else {
                    tb.transition(AfterAfterBody);
                }
            }
            return true;
        }
    },
    InFrameset {
        /* access modifiers changed from: package-private */
        public boolean process(Token t, HtmlTreeBuilder tb) {
            if (HtmlTreeBuilderState.isWhitespace(t)) {
                tb.insert(t.asCharacter());
            } else if (t.isComment()) {
                tb.insert(t.asComment());
            } else if (t.isDoctype()) {
                tb.error(this);
                return false;
            } else if (t.isStartTag()) {
                Token.StartTag start = t.asStartTag();
                String name = start.name();
                if (name.equals(ContentType.SUBTYPE_HTML)) {
                    return tb.process(start, InBody);
                }
                if (name.equals("frameset")) {
                    tb.insert(start);
                } else if (name.equals("frame")) {
                    tb.insertEmpty(start);
                } else if (name.equals("noframes")) {
                    return tb.process(start, InHead);
                } else {
                    tb.error(this);
                    return false;
                }
            } else if (!t.isEndTag() || !t.asEndTag().name().equals("frameset")) {
                if (!t.isEOF()) {
                    tb.error(this);
                    return false;
                } else if (!tb.currentElement().nodeName().equals(ContentType.SUBTYPE_HTML)) {
                    tb.error(this);
                    return true;
                }
            } else if (tb.currentElement().nodeName().equals(ContentType.SUBTYPE_HTML)) {
                tb.error(this);
                return false;
            } else {
                tb.pop();
                if (!tb.isFragmentParsing() && !tb.currentElement().nodeName().equals("frameset")) {
                    tb.transition(AfterFrameset);
                }
            }
            return true;
        }
    },
    AfterFrameset {
        /* access modifiers changed from: package-private */
        public boolean process(Token t, HtmlTreeBuilder tb) {
            if (HtmlTreeBuilderState.isWhitespace(t)) {
                tb.insert(t.asCharacter());
            } else if (t.isComment()) {
                tb.insert(t.asComment());
            } else if (t.isDoctype()) {
                tb.error(this);
                return false;
            } else if (t.isStartTag() && t.asStartTag().name().equals(ContentType.SUBTYPE_HTML)) {
                return tb.process(t, InBody);
            } else {
                if (t.isEndTag() && t.asEndTag().name().equals(ContentType.SUBTYPE_HTML)) {
                    tb.transition(AfterAfterFrameset);
                } else if (t.isStartTag() && t.asStartTag().name().equals("noframes")) {
                    return tb.process(t, InHead);
                } else {
                    if (!t.isEOF()) {
                        tb.error(this);
                        return false;
                    }
                }
            }
            return true;
        }
    },
    AfterAfterBody {
        /* access modifiers changed from: package-private */
        public boolean process(Token t, HtmlTreeBuilder tb) {
            if (t.isComment()) {
                tb.insert(t.asComment());
            } else if (t.isDoctype() || HtmlTreeBuilderState.isWhitespace(t) || (t.isStartTag() && t.asStartTag().name().equals(ContentType.SUBTYPE_HTML))) {
                return tb.process(t, InBody);
            } else {
                if (!t.isEOF()) {
                    tb.error(this);
                    tb.transition(InBody);
                    return tb.process(t);
                }
            }
            return true;
        }
    },
    AfterAfterFrameset {
        /* access modifiers changed from: package-private */
        public boolean process(Token t, HtmlTreeBuilder tb) {
            if (t.isComment()) {
                tb.insert(t.asComment());
            } else if (t.isDoctype() || HtmlTreeBuilderState.isWhitespace(t) || (t.isStartTag() && t.asStartTag().name().equals(ContentType.SUBTYPE_HTML))) {
                return tb.process(t, InBody);
            } else {
                if (!t.isEOF()) {
                    if (t.isStartTag() && t.asStartTag().name().equals("noframes")) {
                        return tb.process(t, InHead);
                    }
                    tb.error(this);
                    return false;
                }
            }
            return true;
        }
    },
    ForeignContent {
        /* access modifiers changed from: package-private */
        public boolean process(Token t, HtmlTreeBuilder tb) {
            return true;
        }
    };
    
    /* access modifiers changed from: private */
    public static String nullString;

    /* access modifiers changed from: package-private */
    public abstract boolean process(Token token, HtmlTreeBuilder htmlTreeBuilder);

    static {
        nullString = String.valueOf(0);
    }

    /* access modifiers changed from: private */
    public static boolean isWhitespace(Token t) {
        if (t.isCharacter()) {
            return isWhitespace(t.asCharacter().getData());
        }
        return false;
    }

    /* access modifiers changed from: private */
    public static boolean isWhitespace(String data) {
        for (int i = 0; i < data.length(); i++) {
            if (!StringUtil.isWhitespace(data.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /* access modifiers changed from: private */
    public static void handleRcData(Token.StartTag startTag, HtmlTreeBuilder tb) {
        tb.insert(startTag);
        tb.tokeniser.transition(TokeniserState.Rcdata);
        tb.markInsertionMode();
        tb.transition(Text);
    }

    /* access modifiers changed from: private */
    public static void handleRawtext(Token.StartTag startTag, HtmlTreeBuilder tb) {
        tb.insert(startTag);
        tb.tokeniser.transition(TokeniserState.Rawtext);
        tb.markInsertionMode();
        tb.transition(Text);
    }

    private static final class Constants {
        /* access modifiers changed from: private */
        public static final String[] DdDt = null;
        /* access modifiers changed from: private */
        public static final String[] Formatters = null;
        /* access modifiers changed from: private */
        public static final String[] Headings = null;
        /* access modifiers changed from: private */
        public static final String[] InBodyEndAdoptionFormatters = null;
        /* access modifiers changed from: private */
        public static final String[] InBodyEndClosers = null;
        /* access modifiers changed from: private */
        public static final String[] InBodyEndTableFosters = null;
        /* access modifiers changed from: private */
        public static final String[] InBodyStartApplets = null;
        /* access modifiers changed from: private */
        public static final String[] InBodyStartDrop = null;
        /* access modifiers changed from: private */
        public static final String[] InBodyStartEmptyFormatters = null;
        /* access modifiers changed from: private */
        public static final String[] InBodyStartInputAttribs = null;
        /* access modifiers changed from: private */
        public static final String[] InBodyStartLiBreakers = null;
        /* access modifiers changed from: private */
        public static final String[] InBodyStartMedia = null;
        /* access modifiers changed from: private */
        public static final String[] InBodyStartOptions = null;
        /* access modifiers changed from: private */
        public static final String[] InBodyStartPClosers = null;
        /* access modifiers changed from: private */
        public static final String[] InBodyStartPreListing = null;
        /* access modifiers changed from: private */
        public static final String[] InBodyStartRuby = null;
        /* access modifiers changed from: private */
        public static final String[] InBodyStartToHead = null;

        private Constants() {
        }

        static {
            InBodyStartToHead = new String[]{"base", "basefont", "bgsound", "command", "link", "meta", "noframes", "script", AnalyticsEvents.PARAMETER_LIKE_VIEW_STYLE, "title"};
            InBodyStartPClosers = new String[]{"address", "article", "aside", "blockquote", "center", "details", "dir", "div", "dl", "fieldset", "figcaption", "figure", "footer", TbsVideoCacheTask.KEY_VIDEO_CACHE_PARAM_HEADER, "hgroup", "menu", "nav", "ol", "p", "section", "summary", "ul"};
            Headings = new String[]{"h1", "h2", "h3", "h4", "h5", "h6"};
            InBodyStartPreListing = new String[]{"pre", "listing"};
            InBodyStartLiBreakers = new String[]{"address", "div", "p"};
            DdDt = new String[]{"dd", "dt"};
            Formatters = new String[]{APDataReportManager.GAMEANDMONTHSINPUT_PRE, "big", "code", "em", "font", "i", "s", "small", "strike", "strong", "tt", "u"};
            InBodyStartApplets = new String[]{"applet", "marquee", "object"};
            InBodyStartEmptyFormatters = new String[]{"area", "br", "embed", IMFriendInfoExViber.IMG, "keygen", "wbr"};
            InBodyStartMedia = new String[]{"param", "source", "track"};
            InBodyStartInputAttribs = new String[]{"name", "action", "prompt"};
            InBodyStartOptions = new String[]{"optgroup", "option"};
            InBodyStartRuby = new String[]{"rp", "rt"};
            InBodyStartDrop = new String[]{ShareConstants.FEED_CAPTION_PARAM, "col", "colgroup", "frame", "head", "tbody", "td", "tfoot", "th", "thead", "tr"};
            InBodyEndClosers = new String[]{"address", "article", "aside", "blockquote", "button", "center", "details", "dir", "div", "dl", "fieldset", "figcaption", "figure", "footer", TbsVideoCacheTask.KEY_VIDEO_CACHE_PARAM_HEADER, "hgroup", "listing", "menu", "nav", "ol", "pre", "section", "summary", "ul"};
            InBodyEndAdoptionFormatters = new String[]{APDataReportManager.GAMEANDMONTHSLIST_PRE, APDataReportManager.GAMEANDMONTHSINPUT_PRE, "big", "code", "em", "font", "i", "nobr", "s", "small", "strike", "strong", "tt", "u"};
            InBodyEndTableFosters = new String[]{"table", "tbody", "tfoot", "thead", "tr"};
        }
    }
}
