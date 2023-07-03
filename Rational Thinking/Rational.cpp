#include <iostream>
#include <sstream>
#include "Rational.h"
#include "Integer.h"

namespace cosc326
{
	// A default constructor that creates Rational with a numerator of 0 and a denominator of 1
	Rational::Rational() : numerator(), denominator("1") {}

	// A constructor that takes a Rational as a parameter and creates a Rational object
	Rational::Rational(const Rational &r) : numerator(r.numerator), denominator(r.denominator) {}

	// A constructor that takes an Integer as a parameter and creates a Rational object
	Rational::Rational(const Integer &a) : numerator(a), denominator("1") {}

	// Constructs a new Rational object with the specified numerator and
	// denominator. The function also performs normalization by adjusting the sign
	// of the Rational to ensure the numerator and denominator have the same sign
	// (if necessary).
	Rational::Rational(const Integer &a, const Integer &b) : numerator(a), denominator(b)
	{
		if (denominator.getIsNegative() && !numerator.getIsNegative()) // Case for when the denominator is negative and the numerator is positive
		{
			denominator.setIsNegative(false);
			numerator.setIsNegative(true);
		}
		else if (denominator.getIsNegative() && numerator.getIsNegative()) // Case for when the denominator is negative and the numerator is negative
		{
			denominator.setIsNegative(false);
			numerator.setIsNegative(false);
		}
	}

	// A constructor that takes three Integers as parameters and creates a Rational object
	Rational::Rational(const Integer &a, const Integer &b, const Integer &c) : denominator(c)
	{
		if (a < Integer()) // Case for when the whole number is negative
		{
			// Set the numerator to the whole number multiplied by the denominator
			Integer multiplier = a * denominator;
			numerator = b + multiplier;

			// Set the multiplier to positive and the numerator to negative
			multiplier.setIsNegative(false);
			numerator.setIsNegative(true);
		}
		else // Case for when the whole number is positive
		{
			// Set the numerator to the whole number multiplied by the denominator
			numerator = b + (a * denominator);
		}
	}

	// Constructs a new Rational object from a string representation.
	// The string representation can be in one of the following formats:
	// - Decimal format: "x.y", where x is the whole part and y is the fractional
	// part.
	// - Fraction format: "x/y", where x is the numerator and y is the denominator.
	// - Integer format: "x", where x is a single integer.
	// If the string contains a decimal point, the resulting rational number will be
	// in decimal format.
	// If the string contains a slash ('/'), the resulting rational number will be
	// in fraction format.
	// Otherwise, the string is interpreted as an integer.
	Rational::Rational(const std::string &s)
	{
		if (s.find(".") != std::string::npos) // Case for when the string contains a whole number and a fraction
		{ 
			// Split the string into two parts, the whole number and the fraction
			std::vector<std::string> whole = splitString(s, ".");
			std::vector<std::string> parts = splitString(s.substr(s.find(".") + 1), "/");
			
			// Set the numerator and denominator
			numerator = Integer(parts[0]);
			denominator = Integer(parts[1]);
			if (Integer(whole[0]) < Integer()) // Case for when the whole number is negative 
			{
				// Set the numerator to the whole number multiplied by the denominator
				Integer multiplier = Integer(whole[0]) * denominator;
				numerator = Integer(parts[0]) + multiplier;

				// Set the multiplier to positive and the numerator to negative
				multiplier.setIsNegative(false);
				numerator.setIsNegative(true);

			}
			else // Case for when the whole number is positive
			{
				// Set the numerator to the whole number multiplied by the denominator
				numerator = Integer(parts[0]) + (Integer(whole[0]) * denominator);
			}
		}
		else if (s.find("/") != std::string::npos) // Case for when the string contains a fraction
		{ 
			// Split the string into two parts, the numerator and the denominator
			std::vector<std::string> parts = splitString(s, "/");

			// Set the numerator and denominator
			numerator = Integer(parts[0]);
			denominator = Integer(parts[1]);
		}
		else // Case for when the string contains a whole number
		{ 
			// Set the numerator and denominator
			numerator = Integer(s);
			denominator = Integer("1");
		}
	}

	// Default destructor
	Rational::~Rational() {}

	// An overload for the unary operator +
	Rational Rational::operator+() const
	{
		return Rational(*this);
	}

	// An overlaod for the unary operator -
	Rational Rational::operator-() const
	{
		return Rational(-numerator, denominator);
	}

	// A getter for the numerator
	const Integer &Rational::getNumerator() const
	{
		return numerator;
	}

	// A getter for the denominator
	const Integer &Rational::getDenominator() const
	{
		return denominator;
	}

	// A setter for the numerator
	void Rational::setNumerator(const Integer &num)
	{
		numerator = num;
	}

	// A setter for the denominator
	void Rational::setDenominator(const Integer &denom)
	{
		denominator = denom;
	}

	// An overload for the assingment operator =
	Rational &Rational::operator=(const Rational &r)
	{
		if (this != &r)
		{
			numerator = r.numerator;
			denominator = r.denominator;
		}
		return *this;
	}

	// An overload for the binary arithmetic operator +
	// To add two rational numbers find a common denominator before performing the operation. 
	// This is done by finding the least common multiple (LCM) of the denominators and then adjusting the numerators accordingly. 
	Rational operator+(const Rational &lhs, const Rational &rhs)
	{
		Integer newNumerator = (lhs.getNumerator() * rhs.getDenominator()) + (rhs.getNumerator() * lhs.getDenominator());
		Integer newDenominator = lhs.getDenominator() * rhs.getDenominator();
		return Rational(newNumerator, newDenominator);
	}

	// An overload for the binary arithmetic operator -
	// To minus two rational numbers find a common denominator before performing the operation. 
	// This is done by finding the least common multiple (LCM) of the denominators and then adjusting the numerators accordingly. 
	Rational operator-(const Rational &lhs, const Rational &rhs)
	{
		Integer newNumerator = (lhs.getNumerator() * rhs.getDenominator()) - (rhs.getNumerator() * lhs.getDenominator());
		Integer newDenominator = lhs.getDenominator() * rhs.getDenominator();
		return Rational(newNumerator, newDenominator);
	}

	// An overload for the binary arithmetic operator *
	// To multiply two rationals multiply the numerators together to get the new numerator and multiply the denominators together to get the new denominator. 
	Rational operator*(const Rational &lhs, const Rational &rhs)
	{
		Integer newNumerator = lhs.getNumerator() * rhs.getNumerator();
		Integer newDenominator = lhs.getDenominator() * rhs.getDenominator();
		return Rational(newNumerator, newDenominator);
	}

	// An overload for the binary arithmetic operator /
	// To divide two rationals you need to take the reciprocal (or multiplicative inverse) of the second fraction and then multiply it by the first fraction. 
	Rational operator/(const Rational &lhs, const Rational &rhs)
	{
		Integer newNumerator = lhs.getNumerator() * rhs.getDenominator();
		Integer newDenominator = lhs.getDenominator() * rhs.getNumerator();
		return Rational(newNumerator, newDenominator);
	}

	// An overload for the compound assignment operator +=
	Rational &Rational::operator+=(const Rational &r)
	{
		*this = *this + r;
		return *this;
	}

	// An overload for the compound assignment operator -=
	Rational &Rational::operator-=(const Rational &r)
	{
		*this = *this - r;
		return *this;
	}

	// An overload for the compound assignment operator *=
	Rational &Rational::operator*=(const Rational &r)
	{
		*this = *this * r;
		return *this;
	}

	// An overload for the compound assignment operator /=
	Rational &Rational::operator/=(const Rational &r)
	{
		*this = *this / r;
		return *this;
	}

	// An overload for the comparison operator ==
	bool operator==(const Rational &lhs, const Rational &rhs)
	{
		return (lhs.getNumerator() == rhs.getNumerator()) && (lhs.getDenominator() == rhs.getDenominator());
	}

	// An overload for the comparison operator !=
	bool operator!=(const Rational &lhs, const Rational &rhs)
	{
		return !operator==(lhs, rhs);
	}

	// An overload for the comparison operator <
	bool operator<(const Rational &lhs, const Rational &rhs)
	{
		return (lhs.getNumerator() * rhs.getDenominator()) < (rhs.getNumerator() * lhs.getDenominator());
	}

	// An overload for the comparison operator <=
	bool operator<=(const Rational &lhs, const Rational &rhs)
	{
		return (lhs.getNumerator() * rhs.getDenominator()) <= (rhs.getNumerator() * lhs.getDenominator());
	}

	// An overload for the comparison operator >
	bool operator>(const Rational &lhs, const Rational &rhs)
	{
		return (lhs.getNumerator() * rhs.getDenominator()) > (rhs.getNumerator() * lhs.getDenominator());
	}

	// An overload for the comparison operator >=
	bool operator>=(const Rational &lhs, const Rational &rhs)
	{
		return (lhs.getNumerator() * rhs.getDenominator()) >= (rhs.getNumerator() * lhs.getDenominator());
	}

	// An overload for the input stream operator >>
	std::ostream &operator<<(std::ostream &os, const Rational &rational)
	{
		if (rational.getDenominator() == Integer()) // Case for when the denominator is 0
		{
			throw std::invalid_argument("Cannot divide by 0");
		}
		if (rational.getNumerator() > rational.getDenominator()) // Case for when the numerator is greater than the denominator
		{
			// Create a temporary rational number
			Rational temp(rational.getNumerator(), rational.getDenominator());
			
			// Simplify the rational number
			temp = temp.simplify();
			
			// Calculate the quotient and remainder
			Integer quotient = temp.getNumerator() / temp.getDenominator();
			Integer remainder = temp.getNumerator() % temp.getDenominator();

			if (remainder == Integer()) // Case for when the remainder is 0
			{
				os << quotient;
			}
			else // Case for when the remainder is not 0
			{
				os << quotient << "." << remainder << "/" << temp.getDenominator();
			}
		}
		else if (rational.getNumerator() == rational.getDenominator()) // Case for when the numerator is equal to the denominator
		{
			os << Integer("1");
		}
		else if (rational.getNumerator() == Integer()) // Case for when the numerator is 0
		{
			os << "0";
		}
		else if (rational.getNumerator() < Integer()) // Case for when the numerator is negative
		{
			// Convert the numerator to a positive number
			Integer positiveNumerator = -rational.getNumerator();

			// Create a temporary rational number with the positive numerator
			Rational temp(positiveNumerator, rational.getDenominator());

			// Simplify the rational number
			temp = temp.simplify();

			// Get the quotient and remainder of the rational number
			Integer quotient = temp.getNumerator() / rational.getDenominator();
			Integer remainder = temp.getNumerator() % rational.getDenominator();
			os << "-";
			if (remainder == Integer()) // Case for when the remainder is 0
			{
				os << quotient;
			}
			else if (quotient > Integer()) // Case for when the quotient is greater than 0
			{
				os << quotient << "." << remainder << "/" << temp.getDenominator();
			}
			else // Case for when the quotient is less than 0
			{
				os << temp.getNumerator() << "/" << temp.getDenominator();
			}
		}
		else // Case for when the numerator is positive
		{
			Rational temp(rational.getNumerator(), rational.getDenominator());
			temp = temp.simplify();
			os << temp.getNumerator() << "/" << temp.getDenominator();
		}
		// Return the output stream
		return os;
	}

	// An overload for the output stream operator <<
	std::istream &operator>>(std::istream &is, Rational &i)
	{
		// Read the input as a string
		std::string input;

		// Check if the input is valid
		if (std::getline(is, input))
		{
			// Convert the string to a rational number
			i = Rational(input);
		}
		// Return the input stream
		return is;
	}

	// A function to simplify a rational number
	Rational Rational::simplify() const
	{
		// Find the greatest common divisor of the numerator and denominator
		Integer gcd = Integer::gcd(getNumerator(), getDenominator());

		// Simplify the numerator and denominator by dividing them by the greatest common divisor
		Integer simplifiedNumerator = getNumerator() / gcd;
		Integer simplifiedDenominator = getDenominator() / gcd;

		// Return the simplified rational number
		return Rational(simplifiedNumerator, simplifiedDenominator);
	}

	// A function to split a string into tokens by a given delimiter
	std::vector<std::string> splitString(const std::string &s, const std::string &delimiter)
	{
		// Split the string into tokens by the delimiter
		std::vector<std::string> tokens;
		size_t start = 0;
		size_t end = s.find(delimiter);

		// Add each token to the vector
		while (end != std::string::npos)
		{
			// Add the token to the vector
			tokens.push_back(s.substr(start, end - start));

			// Update the start and end positions
			start = end + delimiter.length();
			end = s.find(delimiter, start);
		}
		// Add the last token to the vector
		tokens.push_back(s.substr(start));

		// Return the vector of tokens
		return tokens;
	}
}
