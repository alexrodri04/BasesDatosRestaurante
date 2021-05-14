package jpa;

import java.util.List;


import pojos.Rol;
import pojos.Usuario;

public interface ManagerJPA {
	void connect();

	void disconnect();

	List<Rol> getRoles();

	Rol getRolById(int rolId);

	void addUsuario(Usuario usuario);

	void addRol(Rol rol);

	Usuario checkPass(String email, String pass);
	
	void updateUsuario(int id, String nombre);
	
	void deleteUsuario(int id);
	
}

