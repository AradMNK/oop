package Recommender;

import Builder.UserBuilder;
import Login.Loginner;
import Objects.User;
import java.util.*;

public class UserRecommender {
    private static final int NUMBER_OF_RECOMMENDED_USERS = 3;

    private final HashSet<User> followerOfFollowers = new HashSet<>();

    public static String[] recommendUser(){
        UserRecommender userRecommender = new UserRecommender();

        for (String followerUsername: Loginner.loginnedUser.getFollowers()){
            User user = UserBuilder.getUserFromDatabaseWithFollowers(followerUsername);
            for (String followerUsername2 : user.getFollowers()){
                userRecommender.followerOfFollowers.add(UserBuilder.getUserFromDatabaseWithFollowers(followerUsername2));
            }
        }

        userRecommender.followerOfFollowers.removeIf(user -> Loginner.loginnedUser.getFollowers().contains(user.getUsername()));

        User[] candidateUsernames = new User[userRecommender.followerOfFollowers.size()];
        Integer[] scores = new Integer[userRecommender.followerOfFollowers.size()];
        int i = 0;

        for (User candidate : userRecommender.followerOfFollowers) {
            int score = 0;
            for (String followedByUser : Loginner.loginnedUser.getFollowers())
                if (candidate.getFollowers().contains(followedByUser)) score++;
            candidateUsernames[i] = candidate;
            scores[i++] = score;
        }

        final List<User> stringListCopy = Arrays.asList(candidateUsernames);
        ArrayList<User> sortedList = new ArrayList<>(stringListCopy);
        sortedList.sort(Comparator.comparing(user -> scores[stringListCopy.indexOf(user)]));

        return (String[]) Arrays.copyOfRange(sortedList.toArray(),
                0, Math.min(sortedList.size(), NUMBER_OF_RECOMMENDED_USERS));
    }
}
