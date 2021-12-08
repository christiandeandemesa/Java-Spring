package com.christiandemesa.pokebook.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.christiandemesa.pokebook.models.Expense;
import com.christiandemesa.pokebook.services.ExpenseService;

@Controller
public class ExpenseController {
	
	// Injects the ExpenseService as a dependency.
	@Autowired
	ExpenseService expService;
	
	// Passes the expenses List to show all the expenses in index.js.
	@RequestMapping("/expenses")
	public String index(Model model, @ModelAttribute("expense") Expense expense) {
		List<Expense> expenses = expService.allExpenses();
		model.addAttribute("expenses", expenses);
		return "index.jsp";   
	}
	
	// Used @PostMapping as a shorthand for @RequestMapping(value="/expenses", method=RequestMethod.POST).
	@PostMapping("/expenses")
	public String create(Model model, @Valid @ModelAttribute("expense") Expense expense, BindingResult result) {
		// If one of the validations in Expense.java is not met:
		if(result.hasErrors()) {
			// If you do not include this, then the table will disappear.
			List<Expense> expenses = expService.allExpenses();
			model.addAttribute("expenses", expenses);
			// Returns the same page with the error messages showing. If you try to redirect, the error messages will not appear.
			return "index.jsp";
		}
		// Otherwise create the expense and redirect, so the table updates.
		else {
			expService.createExpense(expense);
			return "redirect:/expenses";
		}
	}
	
}