#include "Integer.h"

#include <iostream>
#include <ostream>
#include <string>
#include <vector>

namespace cosc326
{
	// A default constructor that creates Integer with a value of 0
	Integer::Integer() : isNegative(false), digits({0}) {}

	// A copy constructor that creates a deep copy of the Integer passed in
	Integer::Integer(const Integer &i) : isNegative(i.isNegative), digits(i.digits) {}

	// A constructor that takes a string and creates an Integer with the same value
	Integer::Integer(const std::string &s)
	{
		// Declare variables to store the length of the String and the starting index
		int length, startIndex;

		// Check if the String has a sign
		if (s[0] == '+' || s[0] == '-')
		{
			length = s.length() - 1;
			startIndex = 1;
		}
		else
		{
			length = s.length();
			startIndex = 0;
		}
		// Resize the vector of digits to store the Integer
		digits.resize(length);

		// Iterate through the string and populate the array of digits
		int j = 0;
		for (int i = startIndex; i < s.length(); i++)
		{
			digits[j++] = s[i] - '0';
		}
		// Set the sign of the Integer
		isNegative = s[0] == '-';
	}

	// A constructor that takes a bool and a vector of ints and creates an Integer with the same value
	Integer::Integer(bool isNegative, const std::vector<int> &digits) : isNegative(isNegative), digits(digits) {}

	// Default destructor
	Integer::~Integer() {}

	// An overload for the unary operator +
	Integer Integer::operator+() const
	{
		// Return a copy of the current object
		return *this;
	}

	// An overload for the unary operator -
	Integer Integer::operator-() const
	{
		// Create a copy of the current object and toggle the isNegative flag
		Integer result(*this);
		result.setIsNegative(!result.getIsNegative());
		return result;
	}

	// A getter for the isNegative flag
	bool Integer::getIsNegative() const
	{
		return isNegative;
	}

	// A getter for the digits vector
	const std::vector<int> &Integer::getDigits() const
	{
		return digits;
	}

	// A setter for the isNegative flag
	void Integer::setIsNegative(bool negative)
	{
		isNegative = negative;
	}

	// A setter for the digits vector
	void Integer::setDigits(const std::vector<int> &newDigits)
	{
		digits = newDigits;
	}

	// An overload for the assingment operator =
	Integer &Integer::operator=(const Integer &i)
	{
		if (this == &i)
		{ // Self-assignment check
			return *this;
		}
		isNegative = i.isNegative;
		digits = i.digits;
		return *this;
	}

	// An overlaod for the binary arithmetic operator +
	// The operator uses column addition to add the two Integers together
	// Will call the minus method for specific cases
	Integer operator+(const Integer &lhs, const Integer &rhs)
	{
		// Determine the sign of the result
		bool isNegative;

		// Check if the result is negative
		if (lhs.getIsNegative() && !rhs.getIsNegative())
		{
			// Case for (-a) + b
			Integer lhsTemp(lhs);
			lhsTemp.setIsNegative(false);
			return rhs - lhsTemp;
		}
		else if (!lhs.getIsNegative() && rhs.getIsNegative())
		{
			// Case for a + (-b)
			Integer rhsTemp(rhs);
			rhsTemp.setIsNegative(false);
			return lhs - rhsTemp;
		}
		else if (!lhs.getIsNegative() && !rhs.getIsNegative())
		{
			// Case for a + b
			isNegative = false;
		}
		else
		{
			// Case for (-a) + (-b)
			isNegative = true;
		}
		// Determine the biggest and smallest length of the two Integers
		const std::vector<int> &lhsDigits = lhs.getDigits();
		const std::vector<int> &rhsDigits = rhs.getDigits();

		// Declare variables to store the length of the two Integers and the result
		int lhsLength = lhsDigits.size();
		int rhsLength = rhsDigits.size();

		// Result array length
		int resultLength = std::max(lhsLength, rhsLength) + 1;

		// Declare a vector to store the result
		std::vector<int> resultArray(resultLength);

		// Index for iterating through lhs digits
		int lhsIndex = lhsLength - 1;

		// Index for iterating through rhs digits
		int rhsIndex = rhsLength - 1;

		// Carry value for addition
		int carry = 0;

		// Iterate through the digits of the two Integers and add them together
		while (resultLength > 0)
		{
			int sum;
			if (lhsIndex >= 0 && rhsIndex >= 0)
			{
				// Add digits from lhs and rhs along with the carry
				sum = lhsDigits[lhsIndex--] + rhsDigits[rhsIndex--] + carry;
			}
			else if (lhsIndex >= 0)
			{
				// Add digits from lhs along with the carry
				sum = lhsDigits[lhsIndex--] + carry;
			}
			else if (rhsIndex >= 0)
			{
				// Add digits from rhs along with the carry
				sum = rhsDigits[rhsIndex--] + carry;
			}
			else
			{
				// Add the carry
				sum = carry;
			}
			// Determine the digit and update carry if necessary
			resultArray[resultLength - 1] = sum % 10;
			carry = sum / 10;
			resultLength--;
		}
		// Return a new Integer with leading zeros removed
		return Integer(isNegative, removeLeadingZeros(resultArray));
	}

	// An overload for the binary arithmetic operator -
	// The operator uses column subtraction to subtract the two Integers
	// Will call the plus method for specific cases
	Integer operator-(const Integer &lhs, const Integer &rhs)
	{
		if (!lhs.getIsNegative() && rhs.getIsNegative())
		{ // Case for a - (-b)
			Integer lhsTemp(lhs);
			lhsTemp.setIsNegative(false);
			Integer rhsTemp(rhs);
			rhsTemp.setIsNegative(false);
			return lhsTemp + rhsTemp;
		}
		else if (lhs.getIsNegative() && !rhs.getIsNegative())
		{ // Case for (-a) - b
			Integer lhsTemp(lhs);
			lhsTemp.setIsNegative(true);
			Integer rhsTemp(rhs);
			rhsTemp.setIsNegative(true);
			return lhsTemp + rhsTemp;
		}
		else if (lhs.getIsNegative() && rhs.getIsNegative())
		{ // Case for (-a) - (-b)
			Integer rhsTemp(rhs);
			rhsTemp.setIsNegative(false);
			return lhs + rhsTemp;
		}
		else
		{
			// Determine if the returned result is negative
			bool isNegative;

			// Determine the bigger and smaller Integer
			std::vector<int> largerDigitCopy = (lhs >= rhs) ? lhs.getDigits() : rhs.getDigits();
			std::vector<int> &largerDigit = largerDigitCopy;
			const std::vector<int> &smallerDigit = (lhs >= rhs) ? rhs.getDigits() : lhs.getDigits();
			if (lhs >= rhs)
			{
				isNegative = false;
			}
			else
			{
				isNegative = true;
			}
			// Determine the biggest and smallest length of the two Integers
			int biggestLength = largerDigit.size();
			int smallestLength = smallerDigit.size();

			// Index for iterating through larger vector
			int largerIndex = biggestLength - 1;

			// Index for iterating through smaller vector
			int smallerIndex = smallestLength - 1;

			// Create a new vector to store the result
			std::vector<int> resultArray(biggestLength);

			// Iterate through the digits of the two Integers
			int difference = 0;
			while (biggestLength > 0)
			{
				if (smallestLength > 0)
				{
					if (largerDigit[largerIndex] - smallerDigit[smallerIndex] < 0)
					{
						// Add 10 to the digit from the larger Integer
						difference = largerDigit[largerIndex--] - smallerDigit[smallerIndex--] + 10;

						// Find the next non-zero digit in the larger Integer
						int tempIndex = largerIndex;
						while (largerDigit[tempIndex] == 0)
						{
							int tempValue = largerDigit[tempIndex];
							tempValue = 9;
							largerDigit[tempIndex] = tempValue;
							tempIndex--;
						}
						// Subtract the digits from the larger Integer
						largerDigit[tempIndex]--;
					}
					else
					{
						// Subtract the digit from the larger Integer
						difference = largerDigit[largerIndex--] - smallerDigit[smallerIndex--];
					}
				}
				else
				{
					// Subtract the digits from the larger Integer
					difference = largerDigit[largerIndex--];
				}
				// Store the difference in the result array
				resultArray[biggestLength - 1] = difference;
				biggestLength--;
				smallestLength--;
			}
			// Return a new Integer with leading zeros removed
			return Integer(isNegative, removeLeadingZeros(resultArray));
		}
	}

	// An overload for the binary arithmetic operator *
	// The operator uses the Karatsuba algorithm for multiplication
	// It is a divide-and-conquer algorithm that allows for efficient multiplication of two large numbers by reducing the number of recursive multiplications required.
	// The algorithm works by breaking the input numbers into smaller parts and recursively computing partial products.
	// It takes advantage of the observation that the product of two numbers with n digits can be computed using three multiplications of numbers with n / 2 digits.
	Integer operator*(const Integer &lhs, const Integer &rhs)
	{
		// Determine if the result is negative
		bool isNegative = lhs.getIsNegative() ^ rhs.getIsNegative();

		// Get the digits of the two Integers
		const std::vector<int> &lhsDigits = lhs.getDigits();
		const std::vector<int> &rhsDigits = rhs.getDigits();
		int lhsLength = lhsDigits.size();
		int rhsLength = rhsDigits.size();

		// Maximum possible length of the product
		std::vector<int> resultArray(lhsLength + rhsLength);

		// Iterate through the digits of the two Integers
		for (int i = lhsLength - 1; i >= 0; i--)
		{
			// Carry for the current digit
			int carry = 0;
			for (int j = rhsLength - 1; j >= 0; j--)
			{
				// Calculate the product
				int product = lhsDigits[i] * rhsDigits[j] + carry + resultArray[i + j + 1];

				// Store the digit in the result array
				resultArray[i + j + 1] = product % 10;

				// Calculate the carry
				carry = product / 10;
			}
			// Store the carry in the result array
			resultArray[i] += carry;
		}
		// Return a new Integer with leading zeros removed
		return Integer(isNegative, removeLeadingZeros(resultArray));
	}

	// An overload for the binary arithmetic operator /
	// The operator uses the long division algorithm for division
	// The algorithm works by subtracting the divisor from a part of the dividend and repeating this process until the remainder is less than the divisor.
	// The number of times the divisor is subtracted from a part of the dividend is the multiple.
	// The next digit of the quotient is then dragged down and the process is repeated until the dividend is less than the divisor.
	// The multiples are then combined until you get the final quotient.
	Integer operator/(const Integer &lhs, const Integer &rhs)
	{
		// Determine if the result is negative
		bool isNegative = lhs.getIsNegative() ^ rhs.getIsNegative();
		if (isNegative == false && lhs < rhs) // Return zero if the dividend is less than the divisor
		{
			return Integer();
		}
		else if (lhs == rhs) // Return 1 if the dividend is equal to the divisor
		{
			return Integer("1");
		}
		else
		{
			// Create copies of the dividend and divisor
			std::vector<int> dividend = lhs.getDigits();
			std::vector<int> divisor = rhs.getDigits();

			// Create a vector to store the quotient
			std::vector<int> quotient(dividend.size(), 0);

			// Create new Integers using a copy of the dividend and divisor
			int dividendIndex = divisor.size() - 1;
			Integer dividendTemp(false, std::vector<int>(dividend.begin(), dividend.begin() + dividendIndex + 1));
			Integer divisorTemp(false, divisor);

			// Iterate through the digits of the dividend
			while (dividendIndex < dividend.size())
			{
				int multiple = 0;

				// Subtract divisor from dividend until dividend becomes less than divisor
				while ((dividendTemp - divisorTemp) >= Integer())
				{
					dividendTemp = dividendTemp - divisorTemp;
					multiple++;
				}
				quotient[dividendIndex] = multiple; // Store the multiple in the quotient
				dividendIndex++;					// Increment the dividend index

				// Add the next digit from the dividend to the dividendTemp
				if (dividendIndex < dividend.size())
				{
					std::vector<int> temporary;
					if (dividendTemp == Integer())
					{
						temporary.resize(dividendTemp.getDigits().size());
					}
					else
					{
						temporary.resize(dividendTemp.getDigits().size() + 1);
					}
					// Copy digits from dividendTemp to temporary array
					std::copy(dividendTemp.getDigits().begin(), dividendTemp.getDigits().end(), temporary.begin());

					// Add the next digit from the dividend to the temporary array
					temporary[temporary.size() - 1] = dividend[dividendIndex];

					// Update the dividendTemp
					dividendTemp = Integer(false, temporary);
				}
			}
			// Return a new Integer with leading zeros removed
			return Integer(isNegative, removeLeadingZeros(quotient));
		}
	}

	// An overload for the binary arithmetic operators %
	// The operator uses the long division algorithm to calculate the remainder
	// The algorithm works by subtracting the divisor from a part of the dividend and repeating this process until the remainder is less than the divisor.
	Integer operator%(const Integer &lhs, const Integer &rhs)
	{
		// Determine if the result is negative
		bool isNegative = lhs.getIsNegative();
		if (isNegative == false && lhs < rhs)
		{
			return lhs; // Return the dividend if it is less than the divisor
		}
		else
		{
			// Create copies of the dividend and divisor
			std::vector<int> dividend = lhs.getDigits();
			std::vector<int> divisor = rhs.getDigits();

			// Create new Integers using a copy of the dividend and divisor
			int dividendIndex = divisor.size() - 1;
			Integer dividendTemp(false, std::vector<int>(dividend.begin(), dividend.begin() + dividendIndex + 1));
			Integer divisorTemp(false, divisor);

			// Iterate through the digits of the dividend
			while (dividendIndex < dividend.size())
			{
				// Subtract divisor from dividend until dividend becomes less than divisor
				while ((dividendTemp - divisorTemp) >= Integer())
				{

					dividendTemp = dividendTemp - divisorTemp;
				}
				// Add the next digit from the dividend to the dividendTemp
				if (dividendIndex < dividend.size() - 1)
				{
					std::vector<int> temporary;
					if (dividendTemp == Integer())
					{
						temporary.resize(dividendTemp.getDigits().size());
					}
					else
					{
						temporary.resize(dividendTemp.getDigits().size() + 1);
					}
					std::copy(dividendTemp.getDigits().begin(), dividendTemp.getDigits().end(), temporary.begin()); // Copy digits from dividendTemp to temporary array
					temporary[temporary.size() - 1] = dividend[dividendIndex + 1];									// Add the next digit from the dividend
					dividendTemp = Integer(false, temporary);														// Update the dividendTemp
				}
				dividendIndex++; // Increment the dividend index
			}
			return Integer(isNegative, removeLeadingZeros(dividendTemp.getDigits()));
		}
	}

	// An overload for the compound assignment operator +=
	Integer &Integer::operator+=(const Integer &i)
	{
		*this = *this + i;
		return *this;
	}

	// An overload for the compound assignment operator -=
	Integer &Integer::operator-=(const Integer &i)
	{
		*this = *this - i;
		return *this;
	}

	// An overload for the compound assignment operator *=
	Integer &Integer::operator*=(const Integer &i)
	{
		*this = *this * i;
		return *this;
	}

	// An overload for the compound assignment operator /=
	Integer &Integer::operator/=(const Integer &i)
	{
		*this = *this / i;
		return *this;
	}

	// An overload for the compound assignment operator %=
	Integer &Integer::operator%=(const Integer &i)
	{
		*this = *this % i;
		return *this;
	}

	// An overload for the comparison operator ==
	bool operator==(const Integer &lhs, const Integer &rhs)
	{
		if (lhs.getIsNegative() != rhs.getIsNegative()) // Check if the signs are different
		{
			return false;
		}
		// Create references to the digits of the Integers
		const std::vector<int> &lhsDigits = lhs.getDigits();
		const std::vector<int> &rhsDigits = rhs.getDigits();
		if (lhsDigits.size() != rhsDigits.size()) // Check if the number of digits are different
		{
			return false;
		}
		for (size_t i = 0; i < lhsDigits.size(); i++) // Iterate through the digits
		{
			if (lhsDigits[i] != rhsDigits[i]) // Check if the digits are different
			{
				return false;
			}
		}
		return true;
	}

	// An overload for the comparison operator !=
	bool operator!=(const Integer &lhs, const Integer &rhs)
	{
		if (lhs.getIsNegative() != rhs.getIsNegative()) // Check if the signs are different
		{
			return true;
		}
		// Create references to the digits of the Integers
		const std::vector<int> &lhsDigits = lhs.getDigits();
		const std::vector<int> rhsDigits = rhs.getDigits();
		if (lhsDigits.size() != rhsDigits.size()) // Check if the number of digits are different
		{
			return true;
		}
		for (size_t i = 0; i < lhsDigits.size(); ++i) // Iterate through the digits
		{
			if (lhsDigits[i] != rhsDigits[i]) // Check if the digits are different
			{
				return true;
			}
		}
		return false;
	}

	// An overload for the comparison operator <
	bool operator<(const Integer &lhs, const Integer &rhs)
	{
		if (lhs.getIsNegative() && !rhs.getIsNegative()) // Check if rhs is positive and lhs is negative
		{
			return true;
		}
		else if (!lhs.getIsNegative() && rhs.getIsNegative()) // Check if lhs is negative and rhs is positive
		{
			return false;
		}
		else
		{
			// Create references to the digits of the Integers
			const std::vector<int> &lhsDigits = lhs.getDigits();
			const std::vector<int> &rhsDigits = rhs.getDigits();
			if (lhsDigits.size() < rhsDigits.size()) // Check if the number of digits of lhs is less than rhs
			{
				return true;
			}
			else if (lhsDigits.size() > rhsDigits.size()) // Check if the number of digits of lhs is greater than rhs
			{
				return false;
			}
			else
			{
				for (size_t i = 0; i < lhsDigits.size(); i++) // Iterate through the digits
				{
					if (lhsDigits[i] < rhsDigits[i]) // Check if the digit of lhs is less than rhs
					{
						return true;
					}
					else if (lhsDigits[i] > rhsDigits[i]) // Check if the digit of lhs is greater than rhs
					{
						return false;
					}
				}
				return false;
			}
		}
	}

	// An overload for the comparison operator <=
	bool operator<=(const Integer &lhs, const Integer &rhs)
	{
		return (lhs < rhs) || (lhs == rhs);
	}

	// An overload for the comparison operator >
	bool operator>(const Integer &lhs, const Integer &rhs)
	{
		if (lhs.getIsNegative() && !rhs.getIsNegative()) // Check if rhs is positive and lhs is negative
		{
			return false;
		}
		else if (!lhs.getIsNegative() && rhs.getIsNegative()) // Check if lhs is negative and rhs is positive
		{
			return true;
		}
		else
		{
			// Create references to the digits of the Integers
			const std::vector<int> &lhsDigits = lhs.getDigits();
			const std::vector<int> &rhsDigits = rhs.getDigits();
			if (lhsDigits.size() > rhsDigits.size()) // Check if the number of digits of lhs is greater than rhs
			{
				return true;
			}
			else if (lhsDigits.size() < rhsDigits.size()) // Check if the number of digits of lhs is less than rhs
			{
				return false;
			}
			else
			{
				for (size_t i = 0; i < lhsDigits.size(); i++) // Iterate through the digits
				{
					if (lhsDigits[i] > rhsDigits[i]) // Check if the digit of lhs is greater than rhs
					{
						return true;
					}
					else if (lhsDigits[i] < rhsDigits[i]) // Check if the digit of lhs is less than rhs
					{
						return false;
					}
				}
				return false;
			}
		}
	}

	// An overload for the comparison operator >=
	bool operator>=(const Integer &lhs, const Integer &rhs)
	{
		return (lhs > rhs) || (lhs == rhs);
	}

	// An overload for the streaming insertion operator <<
	std::ostream &operator<<(std::ostream &os, const Integer &obj)
	{
		// If the Integer is negative, print a minus sign
		if (obj.getIsNegative())
		{
			os << "-";
		}
		// Print the digits of the Integer
		for (int digit : obj.getDigits())
		{
			os << digit;
		}
		// Return the ostream object
		return os;
	}

	// An overload for the streaming extraction operator >>
	std::istream &operator>>(std::istream &is, Integer &i)
	{
		// Read the input into a string
		std::string input;
		is >> input;

		// Create a new Integer object from the string
		i = Integer(input);

		// Return the istream object
		return is;
	}

	// A function that calculates the greatest common divisor (GCD) of two integers using the Euclidean algorithm.
	Integer Integer::gcd(const Integer &a, const Integer &b)
	{
		// Create two vectors to store the digits of the two Integers
		std::vector<int> largerArray;
		std::vector<int> smallerArray;

		// Determine if a or b is the larger Integer
		if (a > b)
		{
			largerArray = a.getDigits();
			smallerArray = b.getDigits();
		}
		else
		{
			largerArray = b.getDigits();
			smallerArray = a.getDigits();
		}
		Integer largerDigit(false, largerArray);
		Integer smallerDigit(false, smallerArray);

		// If a is equal to b then return b
		if (a == b)
		{
			return a;
		}
		else
		{
			// Find the greatest common divisor using Euclidean algorithm
			while (smallerDigit != Integer())
			{
				Integer temp = smallerDigit;
				smallerDigit = largerDigit % smallerDigit;
				largerDigit = temp;
			}
			// Return the GCD
			return largerDigit;
		}
	}

	// A function that removes leading zeros from a vector of digits representing an integer.
	std::vector<int> removeLeadingZeros(const std::vector<int> &digits)
	{
		// Find the first non-zero digit
		int i = 0;
		while (i < digits.size() && digits[i] == 0)
		{
			i++;
		}
		// If all digits are zero, return an array with one zero
		if (i == digits.size())
		{
			return std::vector<int>{0};
		}
		// Copy the remaining digits to a new array
		std::vector<int> result(digits.begin() + i, digits.end());

		// Return the new array
		return result;
	}
}
