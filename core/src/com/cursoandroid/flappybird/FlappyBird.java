package com.cursoandroid.flappybird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class FlappyBird extends ApplicationAdapter {

	private SpriteBatch batch;
	private Texture passaro, fundo;
	private int contador = 0;


	@Override
	public void create () {

		batch = new SpriteBatch();
		passaro = new Texture("passaro1.png");
		fundo = new Texture("fundo.png");
	}

	@Override
	public void render () {

		contador++;

		batch.begin();

		batch.draw(fundo, 0,0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		batch.draw(passaro, contador, 500);

		batch.end();

	}

}
