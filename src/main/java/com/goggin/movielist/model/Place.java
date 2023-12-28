package com.goggin.movielist.model;

import lombok.ToString;

/***
 * Represents a place found from the Google API search
 */
@ToString
public class Place {
    private String websiteUri;
    private DisplayName displayName;

    public Place() {
    }

    public Place(String websiteUri, DisplayName displayName) {
        this.websiteUri = websiteUri;
        this.displayName = displayName;
    }

    public String getWebsiteUri() {
        return websiteUri;
    }

    public void setWebsiteUri(String websiteUri) {
        this.websiteUri = websiteUri;
    }

    public DisplayName getDisplayName() {
        return displayName;
    }

    public void setDisplayName(DisplayName displayName) {
        this.displayName = displayName;
    }

    public static class DisplayName {
        private String text;
        private String languageCode;

        public DisplayName() {
        }

        public DisplayName(String text, String languageCode) {
            this.text = text;
            this.languageCode = languageCode;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getLanguageCode() {
            return languageCode;
        }

        public void setLanguageCode(String languageCode) {
            this.languageCode = languageCode;
        }
    }
}
