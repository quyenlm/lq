package com.amazonaws.services.s3.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class BucketTaggingConfiguration {
    private List<TagSet> tagSets;

    public BucketTaggingConfiguration() {
        this.tagSets = null;
        this.tagSets = new ArrayList(1);
    }

    public BucketTaggingConfiguration(Collection<TagSet> tagSets2) {
        this.tagSets = null;
        this.tagSets = new ArrayList(1);
        this.tagSets.addAll(tagSets2);
    }

    public BucketTaggingConfiguration withTagSets(TagSet... tagSets2) {
        this.tagSets.clear();
        for (TagSet add : tagSets2) {
            this.tagSets.add(add);
        }
        return this;
    }

    public void setTagSets(Collection<TagSet> tagSets2) {
        this.tagSets.clear();
        this.tagSets.addAll(tagSets2);
    }

    public List<TagSet> getAllTagSets() {
        return this.tagSets;
    }

    public TagSet getTagSet() {
        return this.tagSets.get(0);
    }

    public TagSet getTagSetAtIndex(int index) {
        return this.tagSets.get(index);
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("{");
        sb.append("TagSets: " + getAllTagSets());
        sb.append("}");
        return sb.toString();
    }
}
