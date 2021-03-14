package com.cursoandroid.flappybird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Random;

public class FlappyBird extends ApplicationAdapter {

	private SpriteBatch batch;
	private Random numAleatorio;
	private Texture passaro[], fundo;
	private Texture canoTopo, canoBaixo;

	//variáveis de configuração
	private float variacao = 0;
	private float velocidadeQueda = 0;

	private int larguraTela;
	private int alturaTela;
	private float posicaoInicialVertical;
	private float posicaoHorizontalCano, posicaoVerticalCanoTopo, posicaoVerticalCanoBaixo;
	private float espacoEntreCanos;
	private float tempoRenderizacao;
	private float variacaoCanos;


	@Override
	public void create () {

		batch = new SpriteBatch();
		numAleatorio = new Random();
		passaro = new Texture[3];
		passaro[0] = new Texture("passaro1.png");
		passaro[1] = new Texture("passaro2.png");
		passaro[2] = new Texture("passaro3.png");
		fundo = new Texture("fundo.png");
		canoTopo = new Texture("cano_topo_maior.png");
		canoBaixo = new Texture("cano_baixo_maior.png");

		larguraTela = Gdx.graphics.getWidth();
		alturaTela = Gdx.graphics.getHeight();
		posicaoInicialVertical = alturaTela/2;
		posicaoHorizontalCano = larguraTela - 100;
		posicaoVerticalCanoTopo = (alturaTela / 2);
		posicaoVerticalCanoBaixo = -120;
		espacoEntreCanos = 500;
	}

	@Override
	public void render () {

		tempoRenderizacao = Gdx.graphics.getDeltaTime();

		//fazendo os canos se moverem, reaparecerem e variando a posição deles a cada nova aparição
		posicaoHorizontalCano -= tempoRenderizacao * 300;
		if (posicaoHorizontalCano < -100){
			posicaoHorizontalCano = larguraTela;
			variacaoCanos = numAleatorio.nextInt(500)-250;
		}

        //adicionando "gravidade" e queda ao pássaro
		if (posicaoInicialVertical > 20 || velocidadeQueda < 0) {
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
		variacao += tempoRenderizacao * 7; //getDeltaTime() pega o intervalo de tempo entre os render
		if (variacao > 2) variacao = 0;

		//gerando as imagens e sprites do jogo
		batch.begin();

		batch.draw(fundo, 0,0, larguraTela, alturaTela);
		batch.draw(canoTopo, posicaoHorizontalCano,posicaoVerticalCanoTopo + espacoEntreCanos/2 + variacaoCanos);
		batch.draw(canoBaixo, posicaoHorizontalCano, posicaoVerticalCanoBaixo - espacoEntreCanos/2 + variacaoCanos);
		batch.draw(passaro[(int) variacao], 200, posicaoInicialVertical);

		batch.end();

	}

}
