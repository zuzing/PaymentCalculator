# Optimal Payment Discount Calculator

This project calculates the optimal way to pay for a list of orders using various payment methods (cards and points), maximizing the discounts applied while prioritizing the use of points over cards when possible.

## Overview

Each order can be paid in one of three ways:

- Fully by points
- Fully by card
- Mixed: partially by points and partially by card

## Discount Rules

- If an order is fully paid by points, the discount associated with the `PUNKTY` payment method is applied.
- If an order is fully paid by a card, the discount specific to that card is applied.
- If an order is at least 10% paid by points, a flat 10% discount is applied. No other discounts apply in this case.
- Orders have a list of applicable card promotions.
- The program tries to determine (optimal solution is not guaranteed) the best combination of payment methods to maximize total discount and prefer using points where beneficial.

## Input Format

### Orders (JSON)

```json
[
  { "id": "ORDER1", "value": "100.00", "promotions": ["mZysk"] },
  { "id": "ORDER2", "value": "200.00", "promotions": ["BosBankrut"] },
  { "id": "ORDER3", "value": "150.00", "promotions": ["mZysk", "BosBankrut"] },
  { "id": "ORDER4", "value": "50.00" }
]
```

### Payment Methods (JSON)

```json
[
  { "id": "PUNKTY", "discount": "15", "limit": "100.00" },
  { "id": "mZysk", "discount": "10", "limit": "180.00" },
  { "id": "BosBankrut", "discount": "5", "limit": "200.00" }
]
```

## Output

The program prints the final amount charged on each payment method after discounts are applied.

⚠️ **Warning:** If the algorithm cannot find a way to finance an order, it will print a warning to standard output, which may interfere with some tests.

## Usage

To run the application:

```bash
java -jar app.jar <path_to_orders.json> <path_to_paymentmethods.json>
```

### Example

```bash
java -jar app.jar orders.json paymentmethods.json
```

## Installation

### Prerequisites

- Java 17+
- Gradle

### Build Instructions (Windows/Linux)

Clone the repository:

```bash
git clone zuzing/PaymentCalculator
cd PaymentCalculator
```

Build the JAR file using Gradle:

```bash
./gradlew shadowJar      # Linux/macOS
gradlew.bat shadowJar    # Windows
```

The JAR will be generated at:

```bash
build/libs/app.jar
```

## Algorithm Description

The implemented algorithm is a heuristic and does **not** guarantee an optimal solution.  
While there are unpaid orders, the algorithm:

1. Iterates over each unpaid order
2. Evaluates each available payment method (points or card) for that order
3. Selects and applies the payment method that yields the **largest immediate discount**
4. Marks the order (or its remaining balance) as paid by that method
5. Repeats until all orders are fully financed or no further method can be applied

⚠️ This greedy approach maximizes discount locally at each step but may miss a globally optimal combination.

## Complexity

Let _N_ = number of orders and _M_ = number of payment methods.  
In the worst case, the algorithm performs an evaluation for each unpaid order against every payment method on each iteration.

- **Time Complexity:** O(N × M × iterations)
- Since in the worst case each iteration finances at least one order or portion thereof, the number of iterations is ≤ N, giving O(N² × M) overall.
