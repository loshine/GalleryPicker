package me.loshine.gallerypicker.model;

import java.util.List;

import me.loshine.gallerypicker.entity.MediaBucket;
import me.loshine.gallerypicker.entity.MediaFile;

/**
 * 描    述：
 * 作    者：loshine1992@gmail.com
 * 时    间：2016/12/22
 */
public interface MediaModel {

    List<MediaFile> getImageMediaList(String bucketId, int pageIndex, int pageSize);

    List<MediaBucket> getAllBucket(boolean isImage);

    List<MediaBucket> getImageBucket();

    List<MediaBucket> getVideoBucket();
}
