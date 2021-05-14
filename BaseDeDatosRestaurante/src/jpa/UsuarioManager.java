
package jpa;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;

import pojos.Rol;
import pojos.Usuario;

public class UsuarioManager implements ManagerJPA{
	
	final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	private EntityManagerFactory factory;
	private EntityManager em;
	private final String PERSISTENCE_PROVIDER = "restaurante-provider";
	
	@Override
	public void connect() {
		factory = Persistence.createEntityManagerFactory(PERSISTENCE_PROVIDER);
		em = factory.createEntityManager();
		em.getTransaction().begin();
		em.createNativeQuery("PRAGMA foreign_keys=ON").executeUpdate();
		em.getTransaction().commit();
		if(getRoles().size() == 0) {
			addRol(new Rol("usuario"));
			addRol(new Rol("admin"));
		}
	}

	@Override
	public void addRol(Rol rol) {
		em.getTransaction().begin();
		em.persist(rol);
		em.getTransaction().commit();
	}

	@Override
	public void disconnect() {
		em.close();
		factory.close();
	}

	@Override
	public List<Rol> getRoles() {
		Query q = em.createNativeQuery("SELECT * FROM Roles", Rol.class);
		List<Rol> resultList = q.getResultList();
		return resultList;
	}

	@Override
	public Rol getRolById(int rolId) {
		Query q = em.createNativeQuery("SELECT * FROM Roles WHERE id = ?", Rol.class);
		q.setParameter(1, rolId);
		return (Rol) q.getSingleResult();//Tener cuidado si la id seleccionada no existe
	}

	@Override
	public void addUsuario(Usuario usuario) {
		em.getTransaction().begin();
		em.persist(usuario);
		em.getTransaction().commit();
	}

	@Override
	public Usuario checkPass(String email, String pass) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(pass.getBytes());
			byte[] hash = md.digest();
			Query q = em.createNativeQuery("SELECT * FROM Usuarios WHERE email = ? AND password = ?", Usuario.class);
			q.setParameter(1, email);
			q.setParameter(2, hash);
			Usuario usuario = (Usuario) q.getSingleResult();
			return usuario;
		}catch(NoSuchAlgorithmException | NoResultException e) {
			return null;
		}
	}
	public void updateUsuario(int id, String email) {
		Usuario UsuarioUpdate = em.find(Usuario.class, id);
		UsuarioUpdate.setEmail(email);
		 em.getTransaction().commit();
	
	}
	
	public void deleteUsuario(int id) {
		Usuario user = em.find(Usuario.class, id);
		em.getTransaction().begin();
		em.remove(user);
		em.getTransaction().commit();
	}
}

