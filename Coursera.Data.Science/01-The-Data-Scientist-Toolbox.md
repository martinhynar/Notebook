---
title: "The Data Scientist’s Toolbox"
output: html_document
---

-------

## Video: Types of questions

### Descriptive analysis

* aims to _describe data_, this is, what are the data that we are working with
* **cannot be generalized** without additional statistical modelling

### Exploratory analysis

* to find relationships that were not known before
* to discover new connections
* **Correlation does not mean causation** -> we may find something interresting (new relationships in data) but that does not mean, that it is new generally applicable rule

### Inferential analysis

* Use small sample to reason about bigger population - commonly the goal of statistical modelling.

### Predictive analysis

* by collecting data on some objects X predict what values will have object Y
* This does not mean that if X predicts Y then X causes Y

### Causal analysis

* What happens to to variable X when you variable Y is changed?
* Causal relationships are usually indentified as average effects -> does not necessarily apply on every individual

### Mechanistic analysis

* Understand exact changes in variables that lead to changes in other variables
* Extremelly complex

## Video: What is data?

**Data are values of qualitative or quantitative variables that belong to a set of items**

* By variables, it is meant *measurements* of items - age, height, ...

The most important thing in Data Science is to know the question we are trying to ansew. The second most important thing is the data.

It might happen that data will limit the questions that could be asked, but having only data saves nothing if there is no question.

The data are important, but they does not necesarily need to contain andswer to given question. No matter how big (or small they are). Event overwhelming amount of data does not need to answer a question if they are not the *right data*.

## Video: Experimental design

spurious (false) correlation - we might study two facts that seem to correlate, but in fact they do not because there is some other (hiddent, not measured) fact that influence both

We might see that A -> Z, but in fact thete is unknown variable K such that A <- K -> Z

Keep in mind: **correlation is not causation**

The unknown variable is called **confounding variable**

Experimenting:

* have replication
* measure variability

**Prediction is not inference**