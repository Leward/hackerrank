package main

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
	x         int
	y         int
}

func main() {
	var config config;
	var seqList [][]int
	lastAns := 0

	scanner := bufio.NewScanner(os.Stdin)
	for scanner.Scan() {
		line := scanner.Text()
		if (config.n == 0) {
			config = extractConfig(line)
			seqList = generateListOfEmptySequences(config.n)
		} else {
			query := extractQuery(line)
			sequenceIndex := (query.x ^ lastAns) % config.n
			if(query.queryType == 1) {
				seqList[sequenceIndex] = append(seqList[sequenceIndex], query.y)
			} else {
				elementInSequenceIndex := query.y % len(seqList[sequenceIndex])
				lastAns = seqList[sequenceIndex][elementInSequenceIndex]
				fmt.Println(lastAns)
			}
		}
	}
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

func extractQuery(line string) query {
	parts := strings.Split(line, " ")
	queryType, _ := strconv.ParseInt(parts[0], 10, 0)
	x, _ := strconv.ParseInt(parts[1], 10, 0)
	y, _ := strconv.ParseInt(parts[2], 10, 0)
	return query{queryType: int(queryType),
		x: int(x),
		y: int(y)}
}
