package com.nuricilengir.gammar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Collectors;

/**
 * @author l0ser
 *
 */
public class CFGrammar {

	private HashMap<String, ArrayList<String>> rules;

	/**
	 * @param rules
	 */
	public CFGrammar(HashMap<String, ArrayList<String>> rules) {
		this.rules = rules;
	}

	/**
	 * @param word
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public boolean isAccepted(String word) {
		int token_size = word.length();
		ArrayList<String>[][] ruleSet = new ArrayList[token_size][token_size];

		for (int pos = 0; pos < token_size; pos++)
			ruleSet[0][pos] = getVariables(String.valueOf(word.charAt(pos)));

		for (int line = 1; line < token_size; line++)
			for (int column = 0; column < (token_size - line); column++) {
				ArrayList<String> xrule = new ArrayList<>();
				int line_at = line, col_at = column;

				for (int rule = 0; rule < line; rule++) {
					ArrayList<String> rule_left = ruleSet[rule][column];
					ArrayList<String> rule_right = ruleSet[--line_at][++col_at];
					insertRule(joinRules(rule_left, rule_right), xrule);
				}
				ruleSet[line][column] = xrule;
			}

		return ruleSet[token_size - 1][0].contains("S");
	}

	/**
	 * @param rule_left
	 * @param rule_right
	 * @return
	 */
	private ArrayList<String> joinRules(ArrayList<String> rule_left, ArrayList<String> rule_right) {
		ArrayList<String> rules = new ArrayList<>();

		if (!rule_left.isEmpty() && !rule_right.isEmpty()) {
			for (String left : rule_left) {
				for (String right : rule_right) {
					String rule = left + right;

					insertRule(getVariables(rule), rules);
				}
			}
		}

		return rules;
	}

	/**
	 * @param value
	 * @return
	 */
	private ArrayList<String> getVariables(String value) {
		return rules.keySet().stream().filter(key -> rules.get(key).contains(value))
				.collect(Collectors.toCollection(ArrayList::new));
	}

	/**
	 * @param inputArray
	 * @param outputArray
	 */
	private void insertRule(ArrayList<String> inputArray, ArrayList<String> outputArray) {
		inputArray.stream().filter(key -> outputArray.indexOf(key) == -1).forEach(outputArray::add);
	}

	/**
	 * @return
	 */
	public HashMap<String, ArrayList<String>> getRules() {
		return rules;
	}

	/**
	 * @param rules
	 */
	public void setRules(HashMap<String, ArrayList<String>> rules) {
		this.rules = rules;
	}
}
