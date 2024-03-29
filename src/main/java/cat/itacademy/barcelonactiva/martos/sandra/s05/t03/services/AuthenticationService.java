package cat.itacademy.barcelonactiva.martos.sandra.s05.t03.services;

import cat.itacademy.barcelonactiva.martos.sandra.s05.t03.model.dto.request.SignInRequest;
import cat.itacademy.barcelonactiva.martos.sandra.s05.t03.model.dto.request.SignUpRequest;
import cat.itacademy.barcelonactiva.martos.sandra.s05.t03.model.dto.response.JwtAuthenticationResponse;

public interface AuthenticationService {
    JwtAuthenticationResponse signup(SignUpRequest request);

    JwtAuthenticationResponse signin(SignInRequest request);
}
