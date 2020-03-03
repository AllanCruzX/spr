package com.allan.spr.services.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.allan.spr.domain.Usuario;
import com.allan.spr.dto.AlunoNewDTO;
import com.allan.spr.dto.VoluntarioNewDTO;
import com.allan.spr.repositories.UsuarioRepository;
import com.allan.spr.resources.exception.FieldMessage;
import com.allan.spr.services.validation.utils.BR;

public class VoluntarioInsertValidator implements ConstraintValidator<VoluntarioInsert, VoluntarioNewDTO> {

	@Autowired
	private UsuarioRepository repo;

	@Override
	public void initialize(VoluntarioInsert ann) {
	}

	@Override
	public boolean isValid(VoluntarioNewDTO objDto, ConstraintValidatorContext context) {
		List<FieldMessage> list = new ArrayList<>();

		if (objDto.getCpf() != null && !BR.isValidCPF(objDto.getCpf())) {

			list.add(new FieldMessage("cpf", "CPF inválido"));
		}

		if (objDto.getEmail() != null) {
			Usuario aux = repo.findByEmail(objDto.getEmail());

			if (aux != null) {
				list.add(new FieldMessage("email", "Email já existente"));

			}
		}

// inclua os testes aqui, inserindo erros na lista
		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}
		return list.isEmpty();
	}
}
