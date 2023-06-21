package com.sda.java3.ecommerce.utils;

import lombok.Getter;

@Getter
public class CustomException extends Exception {
  private final String messageKey;
  private final String messageDetail;

  public CustomException(String messageKey, String messageDetail) {
    this.messageDetail = messageDetail;
    this.messageKey = messageKey;
  }
}
