#pragma once

// Most compilers understand the once pragma, but just in case...
#ifndef RATIONAL_H_INCLUDED
#define RATIONAL_H_INCLUDED

#include <iostream>
#include <string>
#include "Integer.h"

namespace cosc326
{

	class Rational
	{

	public:
		Rational();
		Rational(const std::string &str);
		Rational(const Rational &r);
		Rational(const Integer &a);										// a
		Rational(const Integer &a, const Integer &b);					// a/b
		Rational(const Integer &a, const Integer &b, const Integer &c); // a + b/c

		// Destructor
		~Rational();

		// Accessors
		const Integer &getNumerator() const;
		const Integer &getDenominator() const;

		// Mutators
		void setNumerator(const Integer &num);
		void setDenominator(const Integer &denom);

		Rational &operator=(const Rational &r); // q = r;

		// Unary operators
		Rational operator-() const; // -r;
		Rational operator+() const; // +r;

		// Arithmetic assignment operators
		Rational &operator+=(const Rational &r); // q += r;
		Rational &operator-=(const Rational &r); // q -= r;
		Rational &operator*=(const Rational &r); // q *= r;
		Rational &operator/=(const Rational &r); // q /= r;

		Rational simplify() const; // Simplify the rational number

	private:
		Integer numerator; // The numerator of the rational number
		Integer denominator; // The denominator of the rational number
	};

	// Binary operators
	Rational operator+(const Rational &lhs, const Rational &rhs); // lhs + rhs;
	Rational operator-(const Rational &lhs, const Rational &rhs); // lhs - rhs;
	Rational operator*(const Rational &lhs, const Rational &rhs); // lhs * rhs;
	Rational operator/(const Rational &lhs, const Rational &rhs); // lhs / rhs;

	// Stream operators
	std::ostream &operator<<(std::ostream &os, const Rational &i); // std::cout << i << std::endl;
	std::istream &operator>>(std::istream &is, Rational &i);	   // std::cin >> i;

	// Comparison operators
	bool operator<(const Rational &lhs, const Rational &rhs);
	bool operator>(const Rational &lhs, const Rational &rhs);  // lhs > rhs
	bool operator<=(const Rational &lhs, const Rational &rhs); // lhs <= rhs
	bool operator>=(const Rational &lhs, const Rational &rhs); // lhs >= rhs
	bool operator==(const Rational &lhs, const Rational &rhs); // lhs == rhs
	bool operator!=(const Rational &lhs, const Rational &rhs); // lhs != rhs
	
	std::vector<std::string> splitString(const std::string &s, const std::string &delimiter); // Split a string into a vector of strings based on a delimiter
}

#endif
