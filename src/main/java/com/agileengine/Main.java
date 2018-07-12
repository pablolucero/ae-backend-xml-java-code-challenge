package com.agileengine;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.Optional;


public class Main {

    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);
    private static final String DEFAULT_TARGET_ELEMENT_ID = "make-everything-ok-button";
    private static final String CHARSET_NAME = "utf8";


    public static void main(String[] args) throws IOException {
        String inputOriginFilePath = args[0];
        String inputOtherSampleFilePath = args[1];
        String targetElementId = args.length > 2 ? args[2] : DEFAULT_TARGET_ELEMENT_ID;
        LOGGER.info("Target element ID: {}.", targetElementId);
        analyzeXmlAndPrintResult(new File(inputOriginFilePath), new File(inputOtherSampleFilePath), targetElementId);
    }


    public static void analyzeXmlAndPrintResult(File originalHtmlFile, File modifiedHtmlFile, String targetElementId) throws IOException {
        Element targetElement = findElementById(originalHtmlFile, targetElementId);
        Elements candidates = findCandidates(modifiedHtmlFile, targetElement);
        Optional<Element> bestCandidate = findBestCandidate(targetElement, candidates);
        System.out.println(bestCandidate.isPresent() ? bestCandidate.get().cssSelector() : "Match not found.");
    }

    private static Element findElementById(File htmlFile, String targetElementId) throws IOException {
        Document doc = Jsoup.parse(htmlFile, CHARSET_NAME, htmlFile.getAbsolutePath());
        Element element = doc.getElementById(targetElementId);
        if (element == null)
            throw new IllegalArgumentException(String.format("Element with ID '%s' is not found", targetElementId));
        return element;
    }

    private static Elements findCandidates(File htmlFile, Element targetElement) throws IOException {
        Document doc = Jsoup.parse(htmlFile, CHARSET_NAME, htmlFile.getAbsolutePath());
        Element targetAncestorElement = targetElement.parent();
        Elements candidates = new Elements();
        while (candidates.isEmpty() && targetAncestorElement.parent() != null) {
            targetAncestorElement = targetAncestorElement.parent();
            Element searchScope = doc.selectFirst(targetAncestorElement.cssSelector());
            if (searchScope != null) {
                candidates = searchScope.getElementsMatchingOwnText("..*");
            }
        }
        LOGGER.info("Found {} candidates within the scope element {}", candidates.size(), targetAncestorElement.cssSelector());
        return candidates;
    }

    private static Optional<Element> findBestCandidate(Element targetElement, Elements candidates) {
        Element bestCandidate = null;
        int highestSimilarity = 0;
        for (Element candidateElement : candidates) {
            int similarity = calculateSimilarityLevel(targetElement, candidateElement);
            LOGGER.info("Similarity level: {}%, CSS selector: {}", similarity, candidateElement.cssSelector());
            if (bestCandidate == null || similarity > highestSimilarity) {
                highestSimilarity = similarity;
                bestCandidate = candidateElement;
            }
        }
        LOGGER.info("Highest similarity: {}%, CSS selector: {}", highestSimilarity, bestCandidate.cssSelector());
        return Optional.of(bestCandidate);
    }


    private static int calculateSimilarityLevel(Element template, Element candidate) {
        int similarity = 0;
        int maxSimilarity = 1 + template.attributes().size() * 2;
        if (template.tagName().equals(candidate.tagName())) similarity++;
        for (Attribute attribute : template.attributes()) {
            if (candidate.hasAttr(attribute.getKey())) {
                similarity++;
                if (candidate.attr(attribute.getKey()).equals(attribute.getValue())) similarity++;
            }
        }
        return (similarity * 100) / maxSimilarity;
    }

}


