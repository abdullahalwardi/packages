package io.flutter.plugins.videoplayer;

/** Optional parameters for configuring buffering and audio mix behavior. */
public class VideoPlayerOptions {
  /** Whether to allow mixing with other audio (the old default). */
  public final boolean mixWithOthers;

  /** Minimum buffer duration in milliseconds before playback starts. */
  public final int minBufferMs;

  /** Maximum buffer duration in milliseconds to retain in memory. */
  public final int maxBufferMs;

  /** Buffer duration in milliseconds required for initial playback. */
  public final int bufferForPlaybackMs;

  /** Buffer duration in milliseconds required after a rebuffer event. */
  public final int bufferForPlaybackAfterRebufferMs;

  /** 
   * Default constructor (keeps the old behavior). 
   * Values here match ExoPlayer’s defaults under the hood.
   */
  public VideoPlayerOptions() {
    this(
      /* mixWithOthers */ false,
      /* minBufferMs */ 2000,
      /* maxBufferMs */ 10000,
      /* bufferForPlaybackMs */ 1000,
      /* bufferForPlaybackAfterRebufferMs */ 2000
    );
  }

  /**
   * Full constructor called by your generated Pigeon API.
   *
   * @param mixWithOthers allow mixing with other audio
   * @param minBufferMs minimum buffer in ms
   * @param maxBufferMs maximum buffer in ms
   * @param bufferForPlaybackMs buffer required to start playback
   * @param bufferForPlaybackAfterRebufferMs buffer required after rebuffer
   */
  public VideoPlayerOptions(
      boolean mixWithOthers,
      int minBufferMs,
      int maxBufferMs,
      int bufferForPlaybackMs,
      int bufferForPlaybackAfterRebufferMs) {
    this.mixWithOthers = mixWithOthers;
    this.minBufferMs = minBufferMs;
    this.maxBufferMs = maxBufferMs;
    this.bufferForPlaybackMs = bufferForPlaybackMs;
    this.bufferForPlaybackAfterRebufferMs = bufferForPlaybackAfterRebufferMs;
  }
}
