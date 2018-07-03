package com.avatar.util;

import static com.avatar.constant.GameConstants.*;
import static com.avatar.constant.GameMessageConstants.*;
import static com.avatar.constant.GameNationConstants.EARTH_NATION;

import java.io.BufferedReader;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.avatar.character.Aang;
import com.avatar.character.GameCharacter;
import com.avatar.character.Katara;
import com.avatar.character.Sokka;
import com.avatar.exception.GameOverException;
import com.avatar.model.GameLoad;

public class WaterNationUtil {

	private static final Logger LOG = LoggerFactory.getLogger(WaterNationUtil.class);
	private static GameCharacter gameCharacter;
	private static boolean inputLoop;

	public static GameLoad level1(GameLoad gameLoad, BufferedReader buf) throws IOException, GameOverException {
		GameUtil.storyLogInterval(GameUtil.getFormattedMsg(ANSI_RED, MSG_BUNDLE, WATER_NATION_INIT));
		GameUtil.storyLogInterval(GameUtil.getFormattedMsg(MSG_BUNDLE, WATER_NATION_LEVEL1_INIT));
		GameUtil.printLevelAndPoints(gameLoad);
		inputLoop = false;
		while (!inputLoop) {
			LOG.info(GameUtil.getFormattedMsg(ANSI_RED, MSG_BUNDLE, WATER_NATION_LEVEL1_INPUT));
			int choice = Integer.parseInt(buf.readLine().trim());
			switch (choice) {
			case 1:
				gameLoad.getCharacters().add(new Katara());
				gameLoad.setCurrentCharacter(gameLoad.getCharacterByName(KATARA));
				inputLoop = true;
				break;
			case 2:
				gameLoad.getCharacters().add(new Sokka());
				gameLoad.setCurrentCharacter(gameLoad.getCharacterByName(SOKKA));
				inputLoop = true;
				break;
			default:
				GameUtil.incrementCounter(); // increment counter
				break;
			}
		}
		GameUtil.clearCounter(); // clear counter
		gameCharacter = gameLoad.getCurrentCharacter();
		// set 25 points for unlocking character
		GameUtil.msgLogInterval(GameUtil.getFormattedMsg(ANSI_GREEN, MSG_BUNDLE, UNLOCAKED_THE_CHARACTER,
				gameLoad.getCharacterByName(gameCharacter.getName())));
		GameUtil.setGamePoints(gameLoad, TWENTY_FIVE, FIVE);

		// break the iceberg
		inputLoop = false;
		switch (gameCharacter.getName()) {
		case KATARA:
			while (!inputLoop) {
				LOG.info(GameUtil.getFormattedMsg(ANSI_RED, MSG_BUNDLE, WATER_NATION_LEVEL1_BREAK, BREAK, WATER));
				if (buf.readLine().trim().toLowerCase().equalsIgnoreCase(BREAK))
					break;
				else
					GameUtil.incrementCounter(); // increment counter
			}
			GameUtil.clearCounter(); // clear counter
			break;
		case SOKKA:
			while (!inputLoop) {
				LOG.info(GameUtil.getFormattedMsg(ANSI_RED, MSG_BUNDLE, WATER_NATION_LEVEL1_BREAK, SWING, BOOMARANG));
				if (buf.readLine().trim().toLowerCase().equalsIgnoreCase(SWING))
					break;
				else
					GameUtil.incrementCounter(); // increment counter
			}
			GameUtil.clearCounter(); // clear counter
			break;
		}

		// set 25 points for breaking iceberg
		GameUtil.setGamePoints(gameLoad, TWENTY_FIVE, TEN);

		// Avatar returns
		gameCharacter = new Aang();
		GameUtil.addCharacterToGameLoad(gameLoad, gameCharacter);
		gameLoad.setCurrentCharacter(gameCharacter);
		// set 50 points for avatar returns
		GameUtil.msgLogInterval(GameUtil.getFormattedMsg(ANSI_GREEN, MSG_BUNDLE, UNLOCAKED_THE_CHARACTER,
				gameLoad.getCharacterByName(gameCharacter.getName())));
		GameUtil.setGamePoints(gameLoad, FIFTY, TWENTY);
		GameUtil.msgLogInterval(
				GameUtil.getFormattedMsg(ANSI_GREEN, MSG_BUNDLE, YOU_ARA_GAMECHARACTER, gameCharacter.getName()));

		// level completed
		GameUtil.levelCompleted(gameLoad, TWENTY_FIVE, buf);
		return gameLoad;
	}

	public static GameLoad level2(GameLoad gameLoad, BufferedReader buf) throws IOException {
		GameUtil.storyLogInterval(GameUtil.getFormattedMsg(MSG_BUNDLE, WATER_NATION_LEVEL2_INIT, ALPHA));
		GameUtil.printLevelAndPoints(gameLoad);
		String[] directions = { DIRECTION_LEFT, DIRECTION_RIGHT, DIRECTION_FORWARD, DIRECTION_BACKWARD };
		inputLoop = false;
		while (!inputLoop) {
			LOG.info(GameUtil.getFormattedMsg(ANSI_RED, MSG_BUNDLE, WATER_NATION_LEVEL2_INPUT));
			if (buf.readLine().trim().equalsIgnoreCase(directions[(int) (Math.random() * directions.length)])) {
				LOG.info(GameUtil.getFormattedMsg(ANSI_GREEN, MSG_BUNDLE, WATER_NATION_LEVEL2_ALPHA_FOUND, ALPHA));
				// set 25 points for find alpha
				GameUtil.setGamePoints(gameLoad, TWENTY_FIVE, TEN);
				break;
			} else {
				LOG.info(GameUtil.getFormattedMsg(MSG_BUNDLE, WATER_NATION_LEVEL2_ALPHA_NOT_FOUND, ALPHA));
			}
		}

		// level completed
		GameUtil.levelCompleted(gameLoad, FIFTEEN, buf);
		return gameLoad;
	}

	public static GameLoad level3(GameLoad gameLoad, BufferedReader buf) throws IOException {
		GameUtil.storyLogInterval(GameUtil.getFormattedMsg(MSG_BUNDLE, WATER_NATION_LEVEL3_STORY));
		GameUtil.storyLogInterval(GameUtil.getFormattedMsg(MSG_BUNDLE, WATER_NATION_LEVEL3_INIT));
		GameUtil.printLevelAndPoints(gameLoad);

		// init battle ships
		GameUtil.msgLogInterval(GameUtil.getFormattedMsg(ANSI_RED, MSG_BUNDLE, BATTLE_SHIP_INPUT));
		int attemts = BattleShip.initBattle(buf);
		if (attemts <= 5) {
			GameUtil.setGamePoints(gameLoad, HUNDRED, SEVENTY_FIVE);
		} else if (attemts > 5 && attemts <= 8) {
			GameUtil.setGamePoints(gameLoad, SEVENTY_FIVE, FIFTY);
		} else if (attemts > 8 && attemts <= 12) {
			GameUtil.setGamePoints(gameLoad, FIFTY, TWENTY_FIVE);
		} else {
			GameUtil.setGamePoints(gameLoad, TWENTY_FIVE, FIFTEEN);
		}

		// level completed
		GameUtil.levelCompleted(gameLoad, SEVENTY_FIVE, buf);

		// Water nation completed
		gameLoad.setCurrentNation(EARTH_NATION);
		return gameLoad;
	}

}