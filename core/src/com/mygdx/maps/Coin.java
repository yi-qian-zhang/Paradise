package com.mygdx.maps;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.bases.BaseActor;

public class Coin extends BaseActor
{
    public Coin(float x, float y, Stage s)
    {
        super(x, y, s);
        loadAnimationFromSheet("items/coin.png", 1, 6, 0.1f, true);
    }
}
