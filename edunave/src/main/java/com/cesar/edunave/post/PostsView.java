package com.cesar.edunave.post;

import java.util.List;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.Route;

@Route("posts")
public class PostsView extends Div {

    public PostsView(PostService postService) {
        setHeightFull();
        Grid<Post> grid = new Grid<>(Post.class, false);
        grid.addColumn(Post::title).setHeader("Title");
        grid.addColumn(Post::summary).setHeader("Summary");
        grid.addColumn(Post::url).setHeader("URL");
        grid.addColumn(Post::datePublished).setHeader("Date Published");
        grid.addThemeVariants(GridVariant.LUMO_COMPACT);

        List<Post> posts = postService.findAll();
        grid.setItems(posts);

        add(grid);
    }

}
