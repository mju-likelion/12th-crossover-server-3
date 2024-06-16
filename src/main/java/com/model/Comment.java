package com.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "comment")
public class Comment extends BaseEntity {
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private User writer;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Post post;

    @Column(length = 2000, nullable = false)
    private String content;
}
