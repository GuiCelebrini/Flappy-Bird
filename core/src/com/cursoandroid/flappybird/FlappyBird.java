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
	private float posicaoInicialVertical;



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
		posicaoInicialVertical = alturaTela/2;
	}

	@Override
	public void render () {

		//contadorMovimento += 5;

        //adicionando "gravidade" e queda ao pássaro
		if (posicaoInicialVertical > 0 || velocidadeQueda < 0) {
			velocidadeQueda += 0.5;
			posicaoInicialVertical = posicaoInicialVertical - velocidadeQueda;
		}

		//adicionando evento de clique e evitando que o pássaro suba mais que o limite da tela
		if (posicaoInicialVertical < alturaTela) {
			if (Gdx.input.justTouched()) {
				velocidadeQueda -= 12;
			}
		} else {
			posicaoInicialVertical = alturaTela - 50;
		}

		//ajeitando a variação de imagens, fazendo o pássaro bater as asas
		variacao += Gdx.graphics.getDeltaTime() * 7; //getDeltaTime() pega o intervalo de tempo entre os render
		if (variacao > 2) variacao = 0;

		//gerando as imagens e sprites do jogo
		batch.begin();

		batch.draw(fundo, 0,0, larguraTela, alturaTela);
		batch.draw(passaro[(int) variacao], 200, posicaoInicialVertical);

		batch.end();

	}

}
