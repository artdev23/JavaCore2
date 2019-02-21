package ru.geekbrains.utils;


import java.util.regex.Pattern;

import static java.util.regex.Pattern.*;


public interface MessageFormats
{

  String AUTH_PATTERN_MESSAGE = "/auth %s %s";
  String AUTH_SUCCESSFUL_MESSAGE = "/auth successful";
  String AUTH_FAILS_MESSAGE = "/auth fails";
  Pattern AUTH_PATTERN = compile("^/auth (.+) (.+)$");

  String USERLIST_PATTERN = "/userlist";

  String MESSAGE_REGEX = "^/w (\\w+) (.+)";
  Pattern MESSAGE_PATTERN = compile(MESSAGE_REGEX, MULTILINE);

  String MESSAGE_SEND_PATTERN = "/w %s %s";

}