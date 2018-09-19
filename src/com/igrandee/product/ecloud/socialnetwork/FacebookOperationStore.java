package com.igrandee.product.ecloud.socialnetwork;

import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.social.UncategorizedApiException;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.FacebookLink;
import org.springframework.social.facebook.api.FqlResult;
import org.springframework.social.facebook.api.FqlResultMapper;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.igrandee.product.ecloud.viewmodel.socialnetwork.FacebookCountVM;
import com.igrandee.product.ecloud.viewmodel.socialnetwork.FacebookShareVM;

@Component
@Scope("prototype")
public class FacebookOperationStore {

	public String sharePhotoWithLink(FacebookShareVM facebookShareVM,Facebook facebook) {
		MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
		map.add("link", facebookShareVM.getLink());
		map.add("name", facebookShareVM.getName());
		map.add("caption", facebookShareVM.getCaption());
		map.add("description", facebookShareVM.getDescription());
		map.add("message", facebookShareVM.getMessage());
		map.add("picture", facebookShareVM.getPicture());
		return facebook.publish(facebook.userOperations().getUserProfile().getEmail(), "feed", map);

	}
	
	public List<FacebookCountVM> getShareLikeCount(FacebookShareVM facebookShareVM){
		Facebook facebook=new FacebookTemplate();
		List<FacebookCountVM> list=null;
		try{
		list =facebook.fqlOperations().query("select comment_count, like_count, share_count, total_count " +
				"from link_stat where url ='"+facebookShareVM.getLink()+"'",new FqlResultMapper<FacebookCountVM>() {
			public FacebookCountVM mapObject(FqlResult result) {
			FacebookCountVM facebookCountVM=new FacebookCountVM();
			facebookCountVM.setCommentcount(result.getLong("comment_count").toString());
			facebookCountVM.setLikecount(result.getLong("like_count").toString());
			facebookCountVM.setSharecount(result.getLong("share_count").toString());
			facebookCountVM.setTotalcount(result.getLong("total_count").toString());
			return facebookCountVM;
			}			
				});
		return list;
		}
		catch(UncategorizedApiException apiException){
			return list;
		}
	}
	
	public String likeLink(FacebookShareVM facebookShareVM,Facebook facebook) {
		FacebookLink facebookLink=new FacebookLink(facebookShareVM.getLink(), facebookShareVM.getName(), facebookShareVM.getCaption(), facebookShareVM.getDescription());
		String id=facebook.feedOperations().postLink(facebookShareVM.getMessage(), facebookLink);
		facebook.likeOperations().like(id);
		return id;
	}
}
