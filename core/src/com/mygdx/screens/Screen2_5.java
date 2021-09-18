package com.mygdx.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.mygdx.actor.SuperRabbit;
import com.mygdx.bases.BaseActor;
import com.mygdx.bases.BaseGame;
import com.mygdx.bases.BaseScreen;
import com.mygdx.game.ParadiseGame;
import com.mygdx.maps.*;
import com.mygdx.monster.Monster;
import com.mygdx.monster.round_2.Goldeen;
import com.mygdx.monster.round_2.Seaking;
import com.mygdx.tilemap.TileMapActor;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;

/**
 * This Screen represents map 2-5
 * @author Yang Tang
 * @version 1.0
 */
public class Screen2_5 extends LevelScreen {

    static {
        fileName = "maps/map_2/Map 2-5.tmx";
        thisScreenClass = "com.mygdx.screens.Screen2_5";
    }

    @Override
    public void initialize() {
        TileMapActor tma = new TileMapActor(fileName, mainStage);

        for (MapObject obj : tma.getRectangleList("Solid")) {
            MapProperties props = obj.getProperties();
            new Solid((float) props.get("x"), (float) props.get("y"), (float) props.get("width"),
                    (float) props.get("height"), mainStage);
        }

        for (MapObject obj : tma.getTileList("Timer")) {
            MapProperties props = obj.getProperties();
            new Timer((float) props.get("x"), (float) props.get("y"), mainStage);
        }

        for (MapObject obj : tma.getRectangleList("Trick")) {
            MapProperties props = obj.getProperties();
            new Trick((float) props.get("x"), (float) props.get("y"), mainStage);
        }

        for (MapObject obj : tma.getTileList("Tip1")) {
            MapProperties props = obj.getProperties();
            new Tip1((float) props.get("x"), (float) props.get("y"), mainStage);
            Tip1.x = (float) props.get("x");
            Tip1.y = (float) props.get("y");
        }

        for (MapObject obj : tma.getTileList("Tip2")) {
            MapProperties props = obj.getProperties();
            new Tip2((float) props.get("x"), (float) props.get("y"), mainStage);
            Tip2.x = (float) props.get("x");
            Tip2.y = (float) props.get("y");
        }

        for (MapObject obj : tma.getTileList("Mon22")) {
            MapProperties props = obj.getProperties();
            Seaking seaking = new Seaking((float) props.get("x"), (float) props.get("y"), mainStage, "monster/round_2/monster_18/18-1.png");
            if(seaking.getX() < 832)
            {
                seaking.setDirection('h');
                seaking.setMovement(200, 180);
            }
            else
                seaking.setMovement(200, 270);
        }

        for (MapObject obj : tma.getRectangleList("End")) {
            MapProperties props = obj.getProperties();
            new End((float) props.get("x"), (float) props.get("y"), mainStage);
        }

        for (MapObject obj : tma.getRectangleList("End1")) {
            MapProperties props = obj.getProperties();
            new End1((float) props.get("x"), (float) props.get("y"), mainStage);
        }

        for (MapObject obj : tma.getRectangleList("End2")) {
            MapProperties props = obj.getProperties();
            new End2((float) props.get("x"), (float) props.get("y"), mainStage);
        }

        for (MapObject obj : tma.getRectangleList("End3")) {
            MapProperties props = obj.getProperties();
            new End3((float) props.get("x"), (float) props.get("y"), mainStage);
        }

        for (MapObject obj : tma.getRectangleList("Lava")) {
            MapProperties props = obj.getProperties();
            new Lava((float) props.get("x"), (float) props.get("y"), mainStage);
        }

        for (MapObject obj : tma.getRectangleList("Wire")) {
            MapProperties props = obj.getProperties();
            new Wire((float) props.get("x"), (float) props.get("y"), mainStage);
        }

        for (MapObject obj : tma.getRectangleList("Start1")) {
            if (start.equals("Start1")) {
                MapProperties props = obj.getProperties();
                jack = new SuperRabbit((float) props.get("x"), (float) props.get("y"), mainStage);
            }
        }

        for (MapObject obj : tma.getRectangleList("Start2")) {
            if (start.equals("Start2")) {
                MapProperties props = obj.getProperties();
                jack = new SuperRabbit((float) props.get("x"), (float) props.get("y"), mainStage);
            }
        }

        for (MapObject obj : tma.getRectangleList("Start3")) {
            if (start.equals("Start3")) {
                MapProperties props = obj.getProperties();
                jack = new SuperRabbit((float) props.get("x"), (float) props.get("y"), mainStage);
            }
        }

        for (MapObject obj : tma.getRectangleList("Start4")) {
            if (start.equals("Start4")) {
                MapProperties props = obj.getProperties();
                jack = new SuperRabbit((float) props.get("x"), (float) props.get("y"), mainStage);
            }
        }

        jack.toFront();
        gameOver = false;
        time = 60;
        deadTime = 0;
        timeLabel = new Label("Time: " + (int) time, BaseGame.labelStyle);
        timeLabel.setColor(Color.LIGHT_GRAY);
        messageLabel = new Label("Message", BaseGame.labelStyle);
        messageLabel.setVisible(false);
        keyTable = new Table();
        powerLabel = new Label("Power: " + jack.getPower(), BaseGame.labelStyle);
        powerLabel.setColor(Color.RED);

        UITable.pad(20);
        UITable.add(powerLabel);
        UITable.add(keyTable).expandX();
        UITable.add(timeLabel);
        UITable.row();
        UITable.add(messageLabel).colspan(3).expandY();
    }

    public void update(float dt) {

        for (BaseActor end : BaseActor.getList(mainStage, "com.mygdx.maps.End"))
            if (jack.overlaps(end) && Gdx.input.isKeyPressed(Input.Keys.E)) {
                try {
                    ParadiseGame.setActiveScreen((BaseScreen) Class.forName(nextScreenClass).newInstance());
                } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }

        for (BaseActor end1 : BaseActor.getList(mainStage, "com.mygdx.maps.End1"))
            if (jack.overlaps(end1)) {
               start = "Start1";
                ParadiseGame.setActiveScreen(new Screen2_6());
            }


        for (BaseActor end2 : BaseActor.getList(mainStage, "com.mygdx.maps.End2"))
            if (jack.overlaps(end2)) {
                start = "Start2";
                ParadiseGame.setActiveScreen(new Screen2_6());
            }

        for (BaseActor end3 : BaseActor.getList(mainStage, "com.mygdx.maps.End3"))
            if (jack.overlaps(end3)) {
                start = "Start3";
                ParadiseGame.setActiveScreen(new Screen2_6());
            }


        for (BaseActor trick : BaseActor.getList(mainStage, "com.mygdx.maps.Trick"))
            if (jack.overlaps(trick) && Gdx.input.isKeyPressed(Input.Keys.E)) {
                try {
                    ParadiseGame.setActiveScreen((BaseScreen) Class.forName(thisScreenClass).newInstance());
                } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }

        for (BaseActor wire : BaseActor.getList(mainStage, "com.mygdx.maps.Wire"))
            if (jack.overlaps(wire)) {
                if (Gdx.input.isKeyPressed(Input.Keys.R)) {
                    jack.scramble();
                }
            }

        for (BaseActor tip : BaseActor.getList(mainStage, "com.mygdx.maps.Tip1")) {
            if (jack.overlaps(tip) && Gdx.input.isKeyPressed(Input.Keys.Q)) {
                new TipActor(Tip1.x, Tip1.y, mainStage, "items/tips/map_2/tip7.png");
                tip.remove();
            }
        }

        for (BaseActor tip : BaseActor.getList(mainStage, "com.mygdx.maps.Tip2")) {
            if (jack.overlaps(tip) && Gdx.input.isKeyPressed(Input.Keys.Q)) {
                new TipActor(Tip2.x, Tip2.y, mainStage, "items/tips/map_2/tip8.png");
                tip.remove();
            }
        }

        for (BaseActor lava : BaseActor.getList(mainStage, "com.mygdx.maps.Lava")) {
            if (jack.overlaps(lava)) {
                ParadiseGame.backgroundMusic.pause();
                if (isDeathSoundPermitted) {
                    ParadiseGame.deathSound.play();
                    isDeathSoundPermitted = false;
                }
                messageLabel.setText("Dead!");
                messageLabel.setColor(Color.RED);
                messageLabel.setVisible(true);
                jack.remove();
                gameOver = true;
            }
        }

        for (BaseActor monster : BaseActor.getList(mainStage, "com.mygdx.monster.round_2.Seaking")) {
            if (jack.overlaps(monster)) {
                ParadiseGame.backgroundMusic.pause();
                if (isDeathSoundPermitted) {
                    ParadiseGame.deathSound.play();
                    isDeathSoundPermitted = false;
                }
                messageLabel.setText("Dead!");
                messageLabel.setColor(Color.RED);
                messageLabel.setVisible(true);
                jack.remove();
                gameOver = true;
            }
        }

        if (time >= 0) time -= dt;
        timeLabel.setText("Time: " + (int) time);
        powerLabel.setText("Power: " + (int) jack.getPower());

        for (BaseActor timer : BaseActor.getList(mainStage, "com.mygdx.maps.Timer"))
            if (jack.overlaps(timer)) {
                time += 20;
                timer.remove();
            }

        if (time <= 0) {
            ParadiseGame.backgroundMusic.pause();
            if (isDeathSoundPermitted) {
                ParadiseGame.deathSound.play();
                isDeathSoundPermitted = false;
            }
            messageLabel.setText("Time Up - Game Over");
            messageLabel.setColor(Color.RED);
            messageLabel.setVisible(true);
            jack.remove();
            gameOver = true;
        }

        if (gameOver) {
            deadTime += dt;
            if (deadTime >= 3) {
                messageLabel.setText("Press \"ENTER\" to Restart Or \"ESC\" to Quit...");
                messageLabel.setColor(Color.GREEN);
                messageLabel.setVisible(true);
            }
            if (Gdx.input.isKeyPressed(Input.Keys.ENTER)) {
                deadTime = 0;
                gameOver = false;
                messageLabel.setVisible(false);
                isDeathSoundPermitted = true;
                ParadiseGame.backgroundMusic.play();
                try {
                    ParadiseGame.setActiveScreen((BaseScreen) Class.forName(thisScreenClass).newInstance());
                } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            } else if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
                try {
                    BufferedWriter bw = new BufferedWriter(new FileWriter("savings.txt"));
                    bw.write("com.mygdx.screens.Screen2_1");
                    bw.flush();
                    bw.close();
                } catch (IOException exception) {
                    exception.printStackTrace();
                }

                Gdx.app.exit();
            }
        }

        for (BaseActor actor : BaseActor.getList(mainStage, "com.mygdx.maps.Solid")) {
            Solid solid = (Solid) actor;
            if (jack.overlaps(solid) && solid.isEnabled()) {
                Vector2 offset = jack.preventOverlap(solid);

                if (offset != null) {
                    if (Math.abs(offset.x) > Math.abs(offset.y))
                        jack.velocityVec.x = 0;
                    else
                        jack.velocityVec.y = 0;
                }
            }
        }
    }

    public boolean keyDown(int keyKode) {
        if (gameOver)
            return false;
        if (keyKode == Input.Keys.SPACE) {
            jack.swim();
        }
        return false;
    }

}
