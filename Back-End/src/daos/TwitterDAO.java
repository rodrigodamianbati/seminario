package daos;

import java.util.ResourceBundle;

import interfaces.ITwitter;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class TwitterDAO implements ITwitter {

	private ResourceBundle bundle;
	private ConfigurationBuilder cb;
	private Twitter twitter;

	/**
	 * Metodo que configura el acceso a twitter
	 */
	private void configurar() {
		try {
			bundle = ResourceBundle.getBundle("properties/twitter");
			cb = new ConfigurationBuilder();
			cb.setDebugEnabled(true);
			cb.setOAuthConsumerKey(bundle.getString("OAuthConsumerKey"));
			cb.setOAuthConsumerSecret(bundle.getString("OAuthConsumerSecret"));
			cb.setOAuthAccessToken(bundle.getString("OAuthAccessToken"));
			cb.setOAuthAccessTokenSecret(bundle.getString("OAuthAccessTokenSecret"));
			TwitterFactory tf = new TwitterFactory(cb.build());
			twitter = tf.getInstance();
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	/**
	 * metodo que recibe una cadena de caracteres y lo publica en twitter.
	 * 
	 * @param text
	 *            parametro de tipo string
	 */
	@Override
	public void publicar(String text) {
		this.configurar();
		try {
			twitter.updateStatus(text);
		} catch (TwitterException e) {
			throw new RuntimeException(e);
		}

	}
}
