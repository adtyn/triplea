package games.strategy.engine.framework;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import games.strategy.debug.ClientLogger;
import games.strategy.engine.data.GameData;
import games.strategy.engine.data.GameObjectOutputStream;

public class GameDataUtils {
  public static GameData cloneGameData(final GameData data) {
    return cloneGameData(data, false);
  }

  /**
   * Create a deep copy of GameData.
   * <strong>You should have the game data's read or write lock before calling this method</strong>
   */
  public static GameData cloneGameData(final GameData data, final boolean copyDelegates) {
    try {
      final GameDataManager manager = new GameDataManager();
      ByteArrayOutputStream sink = new ByteArrayOutputStream(10000);
      manager.saveGame(sink, data, copyDelegates);
      sink.close();
      final ByteArrayInputStream source = new ByteArrayInputStream(sink.toByteArray());
      sink = null;
      return manager.loadGame(source, null);
    } catch (final IOException ex) {
      ClientLogger.logQuietly(ex);
      return null;
    }
  }

  /**
   * Translate units,territories and other game data objects from one
   * game data into another.
   */
  @SuppressWarnings("unchecked")
  public static <T> T translateIntoOtherGameData(final T object, final GameData translateInto) {
    try {
      ByteArrayOutputStream sink = new ByteArrayOutputStream(1024);
      final GameObjectOutputStream out = new GameObjectOutputStream(sink);
      out.writeObject(object);
      out.flush();
      out.close();
      final ByteArrayInputStream source = new ByteArrayInputStream(sink.toByteArray());
      sink = null;
      final GameObjectStreamFactory factory = new GameObjectStreamFactory(translateInto);
      final ObjectInputStream in = factory.create(source);
      try {
        return (T) in.readObject();
      } catch (final ClassNotFoundException ex) {
        // should never happen
        throw new RuntimeException(ex);
      }
    } catch (final IOException ioe) {
      throw new RuntimeException(ioe);
    }
  }
}
