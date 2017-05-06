package main

import (
	"bufio"
	"os"
	"strings"
	"strconv"
	"fmt"
)

/*
 * Use type int64 for values as they may not fit into an integer
 */

type config struct {
	listSize  int
	nbQueries int
}

type query struct {
	first int
	last  int
	value int64
}

// Time complexity O(n)
func main() {
	scanner := bufio.NewScanner(os.Stdin)
	scanner.Scan()
	config := parseConfig(scanner.Text())

	/*
	 Each updates[i] indicates what value to add to items that list[>=i]
	 This allows to keep tracks of values to change, without updating all the values at each query (we want to avoid expensive nested loops)
	 The first value to update gets assigned +amount
	 The value after the last one to update gets -amount
	 because we want to cancel to effect from the first update to values that should not be updated

	 Example:

	 listSize = 5
	 query = 1 3 100

	 We could update all items in the list: [100 100 100 0 0]
	 But this won't scale very well

	 Instead track changes at the first and after the last items
	 updates = [100 0 0 -100 0 0] Note that updates as one extra item to avoid error when query.last is the last element of the list
	 */
	updates := make([]int64, config.listSize + 1)
	for scanner.Scan() {
		query := parseQuery(scanner.Text())
		updates[query.first - 1] += query.value
		updates[query.last] -= query.value
	}

	// Compute (accumulate) the updates from left to right, and keep the highest value in memory
	// The accumulation won't be the highest value, remember we are going to accumulate with negative values
	var acc int64
	var max int64
	for i := 0; i < config.listSize; i++ {
		acc += updates[i]
		if(acc > max) {
			max = acc
		}
	}

	fmt.Println(max)
}

func parseConfig(line string) config {
	items := strings.Split(line, " ")
	listSize, _ := strconv.ParseInt(items[0], 10, 0)
	nbQueries, _ := strconv.ParseInt(items[1], 10, 0)
	return config{
		listSize: int(listSize),
		nbQueries: int(nbQueries) }
}

func parseQuery(line string) query {
	items := strings.Split(line, " ")
	first, _ := strconv.ParseInt(items[0], 10, 0)
	last, _ := strconv.ParseInt(items[1], 10, 0)
	value, _ := strconv.ParseInt(items[2], 10, 64)
	return query{
		first: int(first),
		last: int(last),
		value: value }
}

func max(values []int64) int64 {
	max := values[0]
	for i := 1; i < len(values); i++ {
		if(values[i]) > max {
			max = values[i]
		}
	}
	return max
}