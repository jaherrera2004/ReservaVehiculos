package com.ReservaVehiculos.controllers.usuarios;

import com.ReservaVehiculos.models.request.usuario.UsuarioRequest;
import com.ReservaVehiculos.services.usuarios.UsuarioIService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/usuarios")
public class UsuarioController {

    private final UsuarioIService usuarioIService;

    @PostMapping
    public void registrarUsuario(@RequestBody UsuarioRequest request){
        usuarioIService.registrarUsuario(request);
    }

    @DeleteMapping("/{id}")
    public void eliminarUsuario(@PathVariable Integer id){
        usuarioIService.eliminarUsuario(id);
    }
}