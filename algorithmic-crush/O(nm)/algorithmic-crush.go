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

// Time complexity O(nm)
func main() {
	scanner := bufio.NewScanner(os.Stdin)
	scanner.Scan()
	config := parseConfig(scanner.Text())
	list := make([]int64, config.listSize)
	for scanner.Scan() {
		query := parseQuery(scanner.Text())
		for i := query.first - 1; i < query.last; i++ {
			list[i] += query.value
		}
	}

	fmt.Println(max(list))
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