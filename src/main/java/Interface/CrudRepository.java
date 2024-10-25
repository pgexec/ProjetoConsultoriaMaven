package Interface;

import java.util.List;


public interface CrudRepository <T>{
	
	public boolean insert(T entidade);
	public boolean update(T entidade);
	public boolean delete(int id);
	public T buscarPorId(int id);
	public List<T> list();	
}
