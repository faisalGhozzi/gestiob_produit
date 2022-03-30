<?php

namespace App\Controller;

use App\Entity\User;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Serializer\Exception\ExceptionInterface;
use Symfony\Component\Serializer\Normalizer\ObjectNormalizer;
use Symfony\Component\Serializer\Serializer;
use Symfony\Component\Routing\Annotation\Route;


class UserController extends AbstractController
{

    /**
     * @Route("/user/json", name="UserJsonAction")
     * @throws ExceptionInterface
     */
    public function user(): Response
    {
        $transport = $this->getDoctrine()->getManager()
            ->getRepository(User::class)->findAll();

        $serializer = new Serializer([new ObjectNormalizer()]);
        $formatted = $serializer->normalize($transport);
        return new JsonResponse($formatted);
    }

    /**
     * @Route("/user/json/{id}", name="UserIdJson")
     * @throws ExceptionInterface
     */
    public function userIdJson($id): JsonResponse
    {
        $user = $this->getDoctrine()->getManager()
            ->getRepository(User::class)->find($id);

        $serializer = new Serializer([new ObjectNormalizer()]);
        $formatted = $serializer->normalize($user);
        return new JsonResponse($formatted);
    }

}