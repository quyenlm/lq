package com.vk.sdk.api;

import com.vk.sdk.api.methods.VKApiAudio;
import com.vk.sdk.api.methods.VKApiDocs;
import com.vk.sdk.api.methods.VKApiFriends;
import com.vk.sdk.api.methods.VKApiGroups;
import com.vk.sdk.api.methods.VKApiMessages;
import com.vk.sdk.api.methods.VKApiPhotos;
import com.vk.sdk.api.methods.VKApiUsers;
import com.vk.sdk.api.methods.VKApiVideo;
import com.vk.sdk.api.methods.VKApiWall;
import com.vk.sdk.api.photo.VKUploadAlbumPhotoRequest;
import com.vk.sdk.api.photo.VKUploadImage;
import com.vk.sdk.api.photo.VKUploadMessagesPhotoRequest;
import com.vk.sdk.api.photo.VKUploadWallPhotoRequest;
import java.io.File;

public class VKApi {
    public static VKApiUsers users() {
        return new VKApiUsers();
    }

    public static VKApiFriends friends() {
        return new VKApiFriends();
    }

    public static VKApiMessages messages() {
        return new VKApiMessages();
    }

    public static VKApiWall wall() {
        return new VKApiWall();
    }

    public static VKApiPhotos photos() {
        return new VKApiPhotos();
    }

    public static VKApiDocs docs() {
        return new VKApiDocs();
    }

    public static VKApiAudio audio() {
        return new VKApiAudio();
    }

    public static VKApiGroups groups() {
        return new VKApiGroups();
    }

    public static VKRequest uploadWallPhotoRequest(File image, long userId, int groupId) {
        return new VKUploadWallPhotoRequest(image, userId, groupId);
    }

    public static VKRequest uploadWallPhotoRequest(VKUploadImage image, long userId, int groupId) {
        return new VKUploadWallPhotoRequest(image, userId, groupId);
    }

    public static VKRequest uploadAlbumPhotoRequest(File image, long albumId, int groupId) {
        return new VKUploadAlbumPhotoRequest(image, albumId, (long) groupId);
    }

    public static VKRequest uploadAlbumPhotoRequest(VKUploadImage image, long albumId, int groupId) {
        return new VKUploadAlbumPhotoRequest(image, albumId, (long) groupId);
    }

    public static VKRequest uploadMessagesPhotoRequest(File image) {
        return new VKUploadMessagesPhotoRequest(image);
    }

    public static VKRequest uploadMessagesPhotoRequest(VKUploadImage image) {
        return new VKUploadMessagesPhotoRequest(image);
    }

    public static VKApiVideo video() {
        return new VKApiVideo();
    }
}
