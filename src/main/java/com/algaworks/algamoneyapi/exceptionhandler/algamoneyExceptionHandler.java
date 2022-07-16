package com.algaworks.algamoneyapi.exceptionhandler;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class algamoneyExceptionHandler extends ResponseEntityExceptionHandler{
	
	@Autowired
	private MessageSource messageSource;
	
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		String mensagemUsuario = messageSource.getMessage("mensagem.invalida", null, LocaleContextHolder.getLocale());
		
		String mensagemDesenvolvedor = ex.getCause().toString();
		
		return handleExceptionInternal(ex, new Erro(mensagemUsuario, mensagemDesenvolvedor), headers, HttpStatus.BAD_REQUEST, request);
	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
		
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		return handleExceptionInternal(ex, ex, headers, HttpStatus.BAD_REQUEST, request);
		
	}
	
	private List<Erro> criarListaDeErros(){
		
		List<Erro> erros = new ArrayList<>();
		
		String mensagemUsuario = "";
		
		String mensagemDesenvolvedor = "";
		
		erros.add(new Erro(mensagemUsuario, mensagemDesenvolvedor));
		
		return erros;
		
	}
	
	public static class Erro{
		
		private String mensagemUsuarioString;
		
		private String mensagemDesenvolvedorString;
		
		public Erro(String mensagemUsuarioString, String mensagemDesenvolvedorString) {
		
			this.mensagemUsuarioString = mensagemUsuarioString;
			
			this.mensagemDesenvolvedorString = mensagemDesenvolvedorString;
			
		}

		public String getMensagemUsuarioString() {
			
			return mensagemUsuarioString;
			
		}

		public String getMensagemDesenvolvedorString() {
			return mensagemDesenvolvedorString;
		}
		
		
		
	}

}
