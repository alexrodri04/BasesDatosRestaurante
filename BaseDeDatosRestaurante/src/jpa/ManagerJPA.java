package jpa;

import java.util.ArrayList;

import pojos.Rol;
import pojos.Usuario;

public interface ManagerJPA {
	void connect();

	void disconnect();

	ArrayList<Rol> getRoles();

	Rol getRolById(int rolId);

	void addUsuario(Usuario usuario);

	void addRol(Rol rol);

	Usuario checkPass(String email, String pass);
}

