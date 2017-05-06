package onego

import (
	"bufio"
	"os"
	"strings"
	"strconv"
	"fmt"
)

type config struct {
	n         int
	nbQueries int
}

type query struct {
	queryType int
	x int
	y int
}

func main() {
	input := readInputFromStdin()

	config := extractConfig(input)
	queries := extractQueries(input, config.nbQueries)
	seqList := generateListOfEmptySequences(config.n)
	var lastAns int = 0

	for i := range queries{
		sequenceIndex := (queries[i].x ^ lastAns) % config.n
		if(queries[i].queryType == 1) {
			seqList[sequenceIndex] = append(seqList[sequenceIndex], queries[i].y)
		} else {
			elementInSequenceIndex := queries[i].y % len(seqList[sequenceIndex])
			lastAns = seqList[sequenceIndex][elementInSequenceIndex]
			fmt.Println(lastAns)
		}
	}
}

func readInputFromStdin() string {
	inputString := ""
	scanner := bufio.NewScanner(os.Stdin)
	for scanner.Scan() {
		inputString = inputString + scanner.Text() + "\n"
	}
	return inputString
}

func generateListOfEmptySequences(n int) [][]int {
	seqList := make([][]int, n)
	for i := range seqList {
		seqList[i] = make([]int, 0)
	}
	return seqList
}

func extractConfig(input string) config {
	var firstLine string = strings.Split(input, "\n")[0]
	var firstLineValues []string = strings.Split(firstLine, " ")
	n, _ := strconv.ParseInt(firstLineValues[0], 10, 0)
	q, _ := strconv.ParseInt(firstLineValues[1], 10, 0)
	return config{n: int(n), nbQueries: int(q)}
}

func extractQueries(input string, nbQueries int) []query {
	lines := strings.Split(input, "\n")
	queries := make([]query, nbQueries)
	for i := 0; i < nbQueries; i++ {
		queries[i] = extractQuery(lines[i + 1])
	}
	return queries
}

func extractQuery(line string) query {
	parts := strings.Split(line, " ")
	queryType, _ := strconv.ParseInt(parts[0], 10, 0)
	x, _ := strconv.ParseInt(parts[1], 10, 0)
	y, _ := strconv.ParseInt(parts[2], 10, 0)
	return query{queryType: int(queryType),
		x: int(x),
		y: int(y)}
}
