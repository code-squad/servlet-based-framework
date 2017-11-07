/*Proudly Developed By Hwi Jun Jeong,
 * Inspired By Bomee, the smartest puppy of the Galaxy.
 * 2017-11-07
 */

package core.jdbc;

import java.util.List;

public interface CrudRepository {
	
	/* public Object findById(long id)
	 * finds an object by primary key(ID). 
	 * must be implemented before any abstract methods or unimplemented methods.
	 */
	public Object findById(long id);
	
	/* public Object insert(Object o)
	 * standard insert operation for universal objects with field values.
	 */
	public Object insert(Object o);
	
	/* public List<Object> findAll()
	 * standard retrieve operation for fetching all objects in the repository.
	 */
	public List<Object> findAll();
}
