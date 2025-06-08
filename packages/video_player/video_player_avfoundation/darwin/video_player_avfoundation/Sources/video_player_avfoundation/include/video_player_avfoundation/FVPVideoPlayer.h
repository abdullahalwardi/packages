// Copyright 2013 The Flutter Authors. All rights reserved.
// Use of this source code is governed by a BSD-style license that can be
// found in the LICENSE file.

#import <AVFoundation/AVFoundation.h>

#import "FVPAVFactory.h"

#if TARGET_OS_OSX
#import <FlutterMacOS/FlutterMacOS.h>
#else
#import <Flutter/Flutter.h>
#endif

NS_ASSUME_NONNULL_BEGIN

/// FVPVideoPlayer manages video playback using AVPlayer.
@interface FVPVideoPlayer : NSObject <FlutterStreamHandler>

/// ← New: store the Pigeon-driven buffer settings
@property(nonatomic, strong, nullable) FVPVideoPlayerOptions *bufferOptions;

/// The Flutter event channel used to communicate with the Flutter engine.
@property(nonatomic) FlutterEventChannel *eventChannel;
/// The AVPlayer instance used for video playback.
@property(nonatomic, readonly) AVPlayer *player;
/// Indicates whether the video player has been disposed.
@property(nonatomic, readonly) BOOL disposed;
/// Indicates whether the video player is set to loop.
@property(nonatomic) BOOL isLooping;
/// The current playback position of the video, in milliseconds.
@property(nonatomic, readonly) int64_t position;

/// Initializes a new instance of FVPVideoPlayer with the given asset, buffer options,
/// AV factory, and registrar.
- (instancetype)initWithAsset:(NSString *)asset
               bufferOptions:(nullable FVPVideoPlayerOptions *)bufferOptions
                    avFactory:(id<FVPAVFactory>)avFactory
                    registrar:(NSObject<FlutterPluginRegistrar> *)registrar;

/// Initializes a new instance of FVPVideoPlayer with the given URL, buffer options,
/// HTTP headers, AV factory, and registrar.
- (instancetype)initWithURL:(NSURL *)url
             bufferOptions:(nullable FVPVideoPlayerOptions *)bufferOptions
                httpHeaders:(nonnull NSDictionary<NSString *, NSString *> *)headers
                  avFactory:(id<FVPAVFactory>)avFactory
                  registrar:(NSObject<FlutterPluginRegistrar> *)registrar;

/// Disposes the video player and releases any resources it holds.
- (void)dispose;

/// Disposes the video player without touching the event channel.
- (void)disposeSansEventChannel;

/// Sets the volume of the video player.
- (void)setVolume:(double)volume;

/// Sets the playback speed of the video player.
- (void)setPlaybackSpeed:(double)speed;

/// Starts playing the video.
- (void)play;

/// Pauses the video.
- (void)pause;

/// Seeks to the specified location in the video and calls the completion handler when done.
- (void)seekTo:(int64_t)location completionHandler:(void (^_Nullable)(BOOL))completionHandler;

@end

NS_ASSUME_NONNULL_END
