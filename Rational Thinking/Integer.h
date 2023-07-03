
// Most compilers understand the once pragma, but just in case...
#ifndef INTEGER_H_INCLUDED
#define INTEGER_H_INCLUDED

#include <iostream>
#include <string>

namespace cosc326
{

	class Integer
	{
	public:
		Integer();												  // Integer i;
		Integer(const Integer &i);								  // Integer j(i);
		Integer(const std::string &s);							  // Integer k("123");
		Integer(bool isNegative, const std::vector<int> &digits); // Constructor with array of digits

		// Destructor
		~Integer();

		// Accessors
		bool getIsNegative() const;
		const std::vector<int> &getDigits() const;

		// Mutators
		void setIsNegative(bool negative);
		void setDigits(const std::vector<int> &newDigits);

		Integer &operator=(const Integer &i); // j = i;

		// Unary operators
		Integer operator-() const; // -j;
		Integer operator+() const; // +j;

		// Arithmetic assignment operators
		Integer &operator+=(const Integer &i); // j += i;
		Integer &operator-=(const Integer &i); // j -= i;
		Integer &operator*=(const Integer &i); // j *= i;
		Integer &operator/=(const Integer &i); // j /= i;
		Integer &operator%=(const Integer &i); // j %= i;

		static Integer gcd(const Integer &a, const Integer &b); // i = gcd(a, b);

	private:
		bool isNegative;		 // true if negative, false if positive
		std::vector<int> digits; // vector of digits, least significant digit first
	};

	// Binary operators
	Integer operator+(const Integer &lhs, const Integer &rhs); // lhs + rhs;
	Integer operator-(const Integer &lhs, const Integer &rhs); // lhs - rhs;
	Integer operator*(const Integer &lhs, const Integer &rhs); // lhs * rhs;
	Integer operator/(const Integer &lhs, const Integer &rhs); // lhs / rhs;
	Integer operator%(const Integer &lhs, const Integer &rhs); // lhs % rhs;

	// Stream operators
	std::ostream &operator<<(std::ostream &os, const Integer &i); // std::cout << i << std::endl;
	std::istream &operator>>(std::istream &is, Integer &i);		  // std::cin >> i;

	// Comparison operators
	bool operator<(const Integer &lhs, const Integer &rhs);	 // lhs < rhs
	bool operator>(const Integer &lhs, const Integer &rhs);	 // lhs > rhs
	bool operator<=(const Integer &lhs, const Integer &rhs); // lhs <= rhs
	bool operator>=(const Integer &lhs, const Integer &rhs); // lhs >= rhs
	bool operator==(const Integer &lhs, const Integer &rhs); // lhs == rhs
	bool operator!=(const Integer &lhs, const Integer &rhs); // lhs != rhs

	// Function to find the greatest common divisor of two integers
	std::vector<int> removeLeadingZeros(const std::vector<int> &digits); // Removes leading zeros from a vector of digits
}

#endif
