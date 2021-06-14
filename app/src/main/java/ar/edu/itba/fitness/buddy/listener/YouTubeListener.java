package ar.edu.itba.fitness.buddy.listener;

import androidx.annotation.NonNull;

import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;

public class YouTubeListener extends AbstractYouTubePlayerListener {
    private String video_id;
    private YouTubePlayer youTubePlayer;

    public YouTubeListener() {

    }

    @Override
    public void onReady(@NonNull YouTubePlayer youTubePlayer) {
        // loading the selected video into the YouTube Player
        this.youTubePlayer = youTubePlayer;
        if (this.video_id != null) {
            youTubePlayer.loadVideo(video_id, 0);
            youTubePlayer.play();
        }
    }

    @Override
    public void onStateChange(@NonNull YouTubePlayer youTubePlayer,
                              @NonNull PlayerConstants.PlayerState state) {
        super.onStateChange(youTubePlayer, state);
        if (state.equals(PlayerConstants.PlayerState.ENDED)) {
            youTubePlayer.seekTo(0); // restart video if it ended
        }
    }

    public void pauseVideo() {
        if (youTubePlayer != null)
            youTubePlayer.pause();
    }

    public void playVideo(String video_id) {
        this.video_id = video_id;
        if (youTubePlayer != null) { // if it is null player is not yet loaded
            youTubePlayer.loadVideo(video_id, 0);
            youTubePlayer.play();
        }
    }
}
