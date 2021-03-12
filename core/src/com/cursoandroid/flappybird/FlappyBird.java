package com.cursoandroid.flappybird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class FlappyBird extends ApplicationAdapter {

	private SpriteBatch batch;
	private Texture passaro[], fundo;

	//variáveis de configuração
	private int contadorMovimento = 0;
	private float variacao = 0;
	private float velocidadeQueda = 0;

	private int larguraTela;
	private int alturaTela;
	private float posicaoInicial;



	@Override
	public void create () {

		batch = new SpriteBatch();
		passaro = new Texture[3];
		passaro[0] = new Texture("passaro1.png");
		passaro[1] = new Texture("passaro2.png");
		passaro[2] = new Texture("passaro3.png");
		fundo = new Texture("fundo.png");

		larguraTela = Gdx.graphics.getWidth();
		alturaTela = Gdx.graphics.getHeight();
		posicaoInicial = alturaTela/2;
	}

	@Override
	public void render () {

		contadorMovimento++;

		if (posicaoInicial > 0) {
			velocidadeQueda += 0.5;
			posicaoInicial = posicaoInicial - velocidadeQueda;
		}

		variacao += Gdx.graphics.getDeltaTime() * 7; //getDeltaTime() pega o intervalo de tempo entre os render
		if (variacao > 2) variacao = 0;

		batch.begin();

		batch.draw(fundo, 0,0, larguraTela, alturaTela);
		batch.draw(passaro[(int) variacao], contadorMovimento, posicaoInicial);

		batch.end();

	}

}
