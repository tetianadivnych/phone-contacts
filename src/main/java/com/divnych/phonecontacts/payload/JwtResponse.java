package com.divnych.phonecontacts.payload;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtResponse {

    private String token;

    private String type = "Bearer";

    private Long id;

    private String login;

    public JwtResponse(String accessToken, Long id, String login) {
        this.token = accessToken;
        this.id = id;
        this.login = login;
    }

}
