/**
 * @author Nuno Pereira
 * @since 1.0.0
 */
module jGame {
	requires transitive java.desktop;
	requires transitive java.logging;
	
	exports jGame.core.launcher;
	exports jGame.core.ui;
	exports jGame.core.ui.hud;
	exports jGame.core.utils;
	exports jGame.core.utils.properties;
	exports jGame.core.entity;
	exports jGame.core.entity.event;
	exports jGame.core.sound;
	exports jGame.core.serializable;
	exports jGame.logging;
}