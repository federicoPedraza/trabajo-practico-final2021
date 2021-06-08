package com.company.models;

import java.util.List;
import java.util.Stack;

//TODO: Finish-up commenting and sub-comments.
public class Comment {
    public String body = "";
    public Account commentator;
    public Publication publication;
    public Comment commentOf = null;
    public Stack<Comment> subComments = new Stack<>();
    public int id;

    public Comment getSubComments(int index)
    {
        return subComments.get(index);
    }

    public void removeAllSubComments()
    {
        subComments.clear();
    }

    public void removeSubCommentsFrom(Account account)
    {
        if(commentator == account) { removeAllSubComments(); }
        for(Comment subComment : subComments)
        {
            if(subComment.commentator.getId() == account.getId())
            {
                subComments.remove(subComment);
            }
        }
    }

    public void addSubComment(Comment comment)
    {
        Comment subComment = subComments.push(comment);
        subComment.id = id + 1;
        subComment.publication = publication;
        subComment.commentOf = this;
    }

    public void removeSubComment(int index)
    {
        for(Comment subComment : subComments)
        {
            if(subComment.id == index)
            {
                subComments.remove(subComment);
                break;
            }
        }
    }
}
