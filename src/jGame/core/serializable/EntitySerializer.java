package jGame.core.serializable;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

import jGame.core.entity.Entity;
import jGame.core.entity.EntityManager;

/**
 * Class responsible for serializing entities.
 * 
 * @author Nuno Pereira
 * @since 1.1.0
 */
public class EntitySerializer {

	static void serializeEntities(File saveFile) {

		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(saveFile))) {

			for (Entity entity : EntityManager.getEntitiesList()) {
				oos.writeObject(entity);
			}

		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
