package hervey.mypackage;

public class ReviewApp {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		double overaAllSentimentSimpleReview = XReview.totalSentiment("SimpleReview.txt");
		System.out.println("overall review of SimpleReview is:  " + overaAllSentimentSimpleReview);
		
		double starRatingSimpleReview = XReview.starRating("SimpleReview.txt");
		System.out.println("Star rating of SimpleReview is:  " + starRatingSimpleReview);
		
	}

}
