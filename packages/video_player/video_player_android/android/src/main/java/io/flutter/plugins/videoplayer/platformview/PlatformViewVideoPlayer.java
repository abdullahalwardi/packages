// Copyright 2013 The Flutter Authors. All rights reserved.
// Use of this source code is governed by a BSD-style license that can be
// found in the LICENSE file.

package io.flutter.plugins.videoplayer.platformview;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
// If you’re on Media3:
import androidx.media3.exoplayer.DefaultLoadControl;
import androidx.media3.exoplayer.ExoPlayer;
// If you’re still on ExoPlayer 2.x, instead:
// import com.google.android.exoplayer2.upstream.DefaultLoadControl;
// import com.google.android.exoplayer2.ExoPlayer;

import io.flutter.plugins.videoplayer.ExoPlayerEventListener;
import io.flutter.plugins.videoplayer.VideoAsset;
import io.flutter.plugins.videoplayer.VideoPlayer;
import io.flutter.plugins.videoplayer.VideoPlayerCallbacks;
import io.flutter.plugins.videoplayer.VideoPlayerOptions;
import io.flutter.view.TextureRegistry.SurfaceProducer;


/**
 * A subclass of {@link VideoPlayer} that adds functionality related to platform view as a way of
 * displaying the video in the app.
 */
public class PlatformViewVideoPlayer extends VideoPlayer {
  @VisibleForTesting
  public PlatformViewVideoPlayer(
      @NonNull VideoPlayerCallbacks events,
      @NonNull MediaItem mediaItem,
      @NonNull VideoPlayerOptions options,
      @NonNull ExoPlayerProvider exoPlayerProvider) {
    super(events, mediaItem, options, /* surfaceProducer */ null, exoPlayerProvider);
  }

  /**
   * Creates a platform view video player.
   *
   * @param context application context.
   * @param events event callbacks.
   * @param asset asset to play.
   * @param options options for playback.
   * @return a video player instance.
   */
@NonNull
  public static PlatformViewVideoPlayer create(
      @NonNull Context context,
      @NonNull VideoPlayerCallbacks events,
      @NonNull VideoAsset asset,
      @NonNull VideoPlayerOptions options) {
    return new PlatformViewVideoPlayer(
      events,
      asset.getMediaItem(),
      options,
      // Here’s the lambda that builds ExoPlayer:
      () -> {
        // 1) Build a LoadControl with your four options:
        DefaultLoadControl loadControl = new DefaultLoadControl.Builder()
            .setBufferDurationsMs(
                /* minBufferMs: */                     options.minBufferMs,
                /* maxBufferMs: */                     options.maxBufferMs,
                /* bufferForPlaybackMs: */             options.bufferForPlaybackMs,
                /* bufferForPlaybackAfterRebufferMs: */options.bufferForPlaybackAfterRebufferMs
            )
            .build();

        // 2) Plug it into the ExoPlayer.Builder before building:
        ExoPlayer.Builder builder = new ExoPlayer.Builder(context)
            .setMediaSourceFactory(asset.getMediaSourceFactory(context))
            .setLoadControl(loadControl);

        return builder.build();
      }
    );
  }

  @NonNull
  @Override
  protected ExoPlayerEventListener createExoPlayerEventListener(
      @NonNull ExoPlayer exoPlayer, @Nullable SurfaceProducer surfaceProducer) {
    return new PlatformViewExoPlayerEventListener(exoPlayer, videoPlayerEvents);
  }
}
