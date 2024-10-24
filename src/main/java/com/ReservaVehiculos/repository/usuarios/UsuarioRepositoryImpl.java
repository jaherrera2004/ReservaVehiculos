package com.ReservaVehiculos.repository.usuarios;

import com.ReservaVehiculos.mappers.UsuarioMapper;
import com.ReservaVehiculos.models.dto.MarcaDto;
import com.ReservaVehiculos.models.dto.UsuarioDto;
import com.ReservaVehiculos.models.entity.UsuarioEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class UsuarioRepositoryImpl implements UsuarioIRepository {

    private final JdbcTemplate jdbcTemplate;
    private final UsuarioMapper usuarioMapper;

    @Override
    public void save(UsuarioEntity usuarioEntity) {

        String sql = "CALL registrar_usuario(?,?,?,?,?,?,?,?,?);";

        jdbcTemplate.update(sql, usuarioEntity.getCedula(),
                usuarioEntity.getNombre(),
                usuarioEntity.getApellido(),
                usuarioEntity.getEdad(),
                usuarioEntity.getEmail(),
                usuarioEntity.getTelefono(),
                usuarioEntity.getContrasenia(),
                usuarioEntity.isActivo(),
                usuarioEntity.getIdRol());
    }

    @Override
    public void desactivarUsuario(Integer id) {

        String sql = "CALL desactivar_usuario(?);";
        jdbcTemplate.update(sql, id);

    }

    @Override
    public void activarUsuario(Integer id) {

        String sql= "CALL activar_usuario(?);";
        jdbcTemplate.update(sql,id);

    }

    @Override
    public boolean existsByEmail(String email) {

        String sql = "SELECT COUNT(*) FROM tbl_usuarios WHERE email=?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, email);

        return count != null && count > 0;

    }

    @Override
    public boolean existsByCedula(String cedula) {

        String sql = "SELECT COUNT(*) FROM tbl_usuarios WHERE cedula=?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, cedula);

        return count != null && count > 0;
    }

    @Override
    public boolean existsById(Integer id) {

        String sql = "SELECT COUNT(*) FROM tbl_usuarios WHERE id=?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, id);

        return count != null && count > 0;
    }

    @Override
    public List<UsuarioDto> findAll() {
        String sql = "SELECT * FROM ver_lista_usuarios;";

        List<UsuarioEntity> listaUsuariosEntity = jdbcTemplate.query(sql, (rs, rowNum) -> {

            UsuarioEntity usuarioEntity = new UsuarioEntity();
            usuarioEntity.setId(rs.getInt("id"));
            usuarioEntity.setCedula(rs.getString("cedula"));
            usuarioEntity.setNombre(rs.getString("nombre"));
            usuarioEntity.setApellido(rs.getString("apellido"));
            usuarioEntity.setEmail(rs.getString("email"));
            usuarioEntity.setTelefono(rs.getString("telefono"));
            usuarioEntity.setEdad(rs.getInt("edad"));
            usuarioEntity.setActivo(rs.getBoolean("activo"));

            return usuarioEntity;
        });

        return listaUsuariosEntity.stream()
                .map(usuarioMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public UsuarioDto findById(Integer id) {
        String sql = "SELECT * FROM ver_lista_usuarios WHERE id=?;";

        UsuarioEntity usuarioEntity= jdbcTemplate.queryForObject(sql,(rs,rowNum)->{
            UsuarioEntity usuarioEntityAux = new UsuarioEntity();
            usuarioEntityAux.setId(rs.getInt("id"));
            usuarioEntityAux.setCedula(rs.getString("cedula"));
            usuarioEntityAux.setNombre(rs.getString("nombre"));
            usuarioEntityAux.setApellido(rs.getString("apellido"));
            usuarioEntityAux.setEmail(rs.getString("email"));
            usuarioEntityAux.setTelefono(rs.getString("telefono"));
            usuarioEntityAux.setEdad(rs.getInt("edad"));
            usuarioEntityAux.setActivo(rs.getBoolean("activo"));

            return usuarioEntityAux;
        },id);

        return usuarioMapper.toDto(usuarioEntity);
    }
}
