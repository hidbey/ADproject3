package iss.workshop.adproject.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.List;

import iss.workshop.adproject.BlogDetailActivity;
import iss.workshop.adproject.BlogsViewingFragment;
import iss.workshop.adproject.Model.Blog;
import iss.workshop.adproject.R;

public class BlogCardAdapter extends RecyclerView.Adapter<BlogCardAdapter.BlogCardViewHolder>{
    List<Blog> blogs;
    private Context context;

    public BlogCardAdapter(List<Blog> blogs, Context context) {
        this.blogs = blogs;
        this.context = context;
    }

    public void setBlogs(List<Blog>blogs){
        this.blogs = blogs;
    }

    @NonNull
    @Override
    public BlogCardAdapter.BlogCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.blog_card, parent, false);
        return new BlogCardAdapter.BlogCardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BlogCardAdapter.BlogCardViewHolder holder, int position) {

        Glide.with(holder.itemView.getContext())
                .load(blogs.get(position).getBlogUser().getProfilePicture())
                .circleCrop()
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        if (e != null) {
                            // 这里记录详细的异常原因到日志
                            e.logRootCauses("Glide"); // "Glide" 是日志标签
                            // 打印异常的堆栈跟踪
                            e.printStackTrace();
                        }
                        // 返回 false 让 Glide 处理错误的占位图
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        // 图片加载成功
                        return false;
                    }
                })
                .into(holder.imageView);
        Glide.with(holder.itemView.getContext())
                .load(blogs.get(position).getImage())
                .into(holder.coverView);

        holder.blogTitle.setText(blogs.get(position).getContentTitle());
        holder.blogAuthor.setText(blogs.get(position).getBlogUser().getDisplayName());
        holder.blogLikes.setText(String.valueOf(blogs.get(position).getBlogLikeCount()));
        holder.blogComments.setText(String.valueOf(blogs.get(position).getBlogCommentCount()));
    }

    @Override
    public int getItemCount() {
        return blogs.size();
    }

    class BlogCardViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imageView,coverView;
        TextView blogTitle;
        TextView blogAuthor;
        TextView blogLikes;
        TextView blogComments;


        public BlogCardViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.profile_picture);
            blogTitle = itemView.findViewById(R.id.item_title);
            blogAuthor = itemView.findViewById(R.id.item_username);
            blogLikes = itemView.findViewById(R.id.blogLikes);
            blogComments = itemView.findViewById(R.id.blogComments);
            coverView = itemView.findViewById(R.id.item_cover);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                Blog blog = blogs.get(position);
                // 启动 BlogDetailActivity，并传递标题数据
                Intent intent = new Intent(context, BlogDetailActivity.class);
                intent.putExtra("blogId", blog.getBlogId());
                intent.putExtra("blogInList", blog); // TODO new new new
                intent.putExtra("position", position); // TODO new new new
                intent.putExtra("from","home");
                context.startActivity(intent);
            }
        }
    }
}


