package com.cursoandroid.flappybird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

import java.util.Random;

public class FlappyBird extends ApplicationAdapter {

	private SpriteBatch batch;
	private Random numAleatorio;
	private Texture passaro[], fundo, gameOver;
	private Texture canoTopo, canoBaixo;
	private BitmapFont fonte, mensagemReinicio;
	private Circle passaroCirculo;
	private Rectangle retanguloCanoTopo, retanguloCanoBaixo;
	//private ShapeRenderer shape;

	//variáveis de configuração, separadas pelo tipo
	private boolean marcouPonto = false; //caso o cano passe do pássaro se tornará true

	private int estadoJogo = 0; //0 -> jogo prestes a iniciar, 1 -> jogo rodando, 2 -> game over
	private int pontuacao = 0;
	private int larguraTela;
	private int alturaTela;

	private float variacao = 0;
	private float velocidadeQueda = 0;
	private float posicaoInicialVertical;
	private float posicaoHorizontalCano, posicaoVerticalCanoTopo, posicaoVerticalCanoBaixo;
	private float espacoEntreCanos;
	private float tempoRenderizacao;
	private float variacaoCanos;


	@Override
	public void create () { //variáveis separadas pelos tipos

		batch = new SpriteBatch();
		numAleatorio = new Random();
		//shape = new ShapeRenderer();

		fonte = new BitmapFont();
		fonte.setColor(Color.WHITE);
		fonte.getData().setScale(6);
		mensagemReinicio = new BitmapFont();
		mensagemReinicio.setColor(Color.WHITE);
		mensagemReinicio.getData().setScale(3);

		passaroCirculo = new Circle();
		retanguloCanoTopo = new Rectangle();
		retanguloCanoBaixo = new Rectangle();

		passaro = new Texture[3];
		passaro[0] = new Texture("passaro1.png");
		passaro[1] = new Texture("passaro2.png");
		passaro[2] = new Texture("passaro3.png");
		fundo = new Texture("fundo.png");
		canoTopo = new Texture("cano_topo_maior.png");
		canoBaixo = new Texture("cano_baixo_maior.png");
		gameOver = new Texture("game_over.png");

		larguraTela = Gdx.graphics.getWidth();
		alturaTela = Gdx.graphics.getHeight();
		posicaoInicialVertical = alturaTela/2;
		posicaoHorizontalCano = larguraTela;
		posicaoVerticalCanoTopo = (alturaTela / 2);
		posicaoVerticalCanoBaixo = -120;
		espacoEntreCanos = 500;
	}

	@Override
	public void render () {

		tempoRenderizacao = Gdx.graphics.getDeltaTime();

		//selecionando o que vai ocorrer e o que não vai enquando o jogo não começa
		if (estadoJogo == 0){

			if (Gdx.input.justTouched()) {
				estadoJogo = 1;
			}

		} else {

			//adicionando "gravidade" e queda ao pássaro
			if (posicaoInicialVertical > 20 || velocidadeQueda < 0) {
				velocidadeQueda += 0.5;
				posicaoInicialVertical = posicaoInicialVertical - velocidadeQueda;
			}

			//o que vai acontecer enquanto o jogo estiver rodando
			if (estadoJogo == 1) {

				//fazendo os canos se moverem, reaparecerem, variando a posição deles a cada nova aparição e configurando o marcouPonto
				posicaoHorizontalCano -= tempoRenderizacao * 300;
				if (posicaoHorizontalCano < -100) {
					posicaoHorizontalCano = larguraTela;
					marcouPonto = false;
					variacaoCanos = numAleatorio.nextInt(500) - 250;
				}

				//caso o cano tenha passado do pássaro, serão marcados pontos
				if (posicaoHorizontalCano < 200 && marcouPonto == false) {
					pontuacao += 1;
					marcouPonto = true;
				}

				//adicionando evento de clique e evitando que o pássaro suba mais que o limite da tela
				if (posicaoInicialVertical < alturaTela) {
					if (Gdx.input.justTouched()) {
						velocidadeQueda -= 12;
					}
				} else {
					posicaoInicialVertical = alturaTela - 50;
				}

			}

			//o que vai acontecer quando der game over
			if (estadoJogo == 2) {
				if (Gdx.input.justTouched()) {
					velocidadeQueda = 0;
					posicaoInicialVertical = alturaTela / 2;
					posicaoHorizontalCano = larguraTela;
					posicaoVerticalCanoTopo = (alturaTela / 2);
					posicaoVerticalCanoBaixo = -120;
					pontuacao = 0;
					estadoJogo = 0;
				}
			}
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
		fonte.draw(batch, String.valueOf(pontuacao) ,(larguraTela/2)-25, alturaTela-75);

		if (estadoJogo == 2){ //será mostrado apenas na tela game over
			batch.draw(gameOver, (larguraTela/2)-(gameOver.getWidth()/2) , alturaTela/2);
			mensagemReinicio.draw(batch, "Toque para reiniciar", (larguraTela/2)-200, (alturaTela/2)-(gameOver.getHeight()/2));
		}

		batch.end();

		//gerando formas para servirem de colisões
		passaroCirculo.set(200 + (passaro[0].getWidth()/2), posicaoInicialVertical + (passaro[0].getHeight()/2), 35);
		retanguloCanoTopo.set(
				posicaoHorizontalCano, posicaoVerticalCanoTopo + espacoEntreCanos/2 + variacaoCanos,
				canoTopo.getWidth(), canoTopo.getHeight()
		);
		retanguloCanoBaixo.set(
				posicaoHorizontalCano, posicaoVerticalCanoBaixo - espacoEntreCanos/2 + variacaoCanos,
				canoBaixo.getWidth(), canoBaixo.getHeight()
		);

		//desenhando as formas
		/*shape.begin(ShapeRenderer.ShapeType.Filled);

		shape.circle(passaroCirculo.x, passaroCirculo.y, passaroCirculo.radius);
		shape.rect(retanguloCanoTopo.x, retanguloCanoTopo.y, retanguloCanoTopo.width, retanguloCanoTopo.height);
		shape.rect(retanguloCanoBaixo.x, retanguloCanoBaixo.y, retanguloCanoBaixo.width, retanguloCanoBaixo.height);

		shape.setColor(Color.BLUE);

		shape.end();*/

		//testando colisão, se for true, o game passará para o estado de game over
		if (Intersector.overlaps(passaroCirculo, retanguloCanoTopo) ||
				Intersector.overlaps(passaroCirculo, retanguloCanoBaixo) ||
				posicaoInicialVertical <= 5) {
			estadoJogo = 2;
		}


	}

}
