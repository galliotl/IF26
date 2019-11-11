package utt.if26.bardcamp.util;

import androidx.annotation.Nullable;

import utt.if26.bardcamp.db.Entity.User;

/**
 * Authentication result : success (user details) or error message.
 */
public class LoginResult {
    @Nullable
    private User success;
    @Nullable
    private Integer error;

    public LoginResult(@Nullable Integer error) {
        this.error = error;
    }
    // ! can be optimised using a UserView todo: create that userView
    public LoginResult(@Nullable User success) {
        this.success = success;
    }

    @Nullable
    public User getSuccess() {
        return success;
    }

    @Nullable
    public Integer getError() {
        return error;
    }
}
